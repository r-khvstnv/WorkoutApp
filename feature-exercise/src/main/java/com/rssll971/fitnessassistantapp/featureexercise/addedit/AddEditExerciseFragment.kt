/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureexercise.addedit

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rssll971.fitnessassistantapp.core.base.BaseFragment
import com.rssll971.fitnessassistantapp.core.utils.loadImage
import com.rssll971.fitnessassistantapp.coredata.models.Exercise
import com.rssll971.fitnessassistantapp.core.R as RCore

import com.rssll971.fitnessassistantapp.featureexercise.databinding.FragmentAddEditExercisesBinding
import com.rssll971.fitnessassistantapp.featureexercise.utils.Constants
import com.rssll971.fitnessassistantapp.featureexercise.di.FeatureExerciseComponentsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.*
import javax.inject.Inject

internal class AddEditExerciseFragment : BaseFragment() {
    private var _binding: FragmentAddEditExercisesBinding? = null
    private val binding get() = _binding!!

    @Inject
    internal lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AddEditExerciseViewModel> { vmFactory }

    private val args: AddEditExerciseFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        ViewModelProvider(this)
            .get<FeatureExerciseComponentsViewModel>()
            .addEditExerciseComponent.inject(this)
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.exerciseId.let {
            id ->
            if (id != -5){
                viewModel.requestExerciseForUpdating(id = id)
            }
        }


        with(binding){
            ivImage.setOnClickListener {
                galleryLauncher()
            }
            ivDeleteImage.setOnClickListener {
                viewModel.deleteCurrentImage()
            }
            btnCreate.setOnClickListener {
                requestExerciseSaving()
            }
            btnDelete.setOnClickListener {
                viewModel.deleteExercise()
            }
        }


        viewModel.isExerciseShouldBeUpdated.observe(viewLifecycleOwner){
            isForUpdate ->
            with(binding){
                /*If exercise for updating is available,
                btn Delete will be visible and btn Create will have "Update" label*/
                if (isForUpdate){
                    btnCreate.text = getString(RCore.string.action_update)
                    btnDelete.visibility = View.VISIBLE
                } else{
                    btnCreate.text = getString(RCore.string.action_create)
                    btnDelete.visibility = View.GONE
                }
            }
        }
        viewModel.exerciseForUpdating.observe(viewLifecycleOwner){
            exercise ->
            exercise?.let {
                with(binding){
                    etTitle.setText(it.name)
                    etDescription.setText(it.description)
                    /**Image observes using separate method*/
                }
            }
        }
        viewModel.imagePath.observe(viewLifecycleOwner){
            path ->
            path?.let {
                binding.ivImage.loadImage(it)
                /*Button for image deleting will appear only if image already added*/
                if (it.isNotEmpty()){
                    binding.ivDeleteImage.visibility = View.VISIBLE
                } else{
                    binding.ivDeleteImage.visibility = View.GONE
                }
            }
        }


        /**Next 3 observers on true - show snack bar with corresponding message and
         * after request navigateUp by navigation graph*/
        viewModel.isSaved.observe(viewLifecycleOwner){
                saved ->
            if (saved){
                showSnackBarMessage(getString(RCore.string.result_saved))
                navigateUp()
            }
        }
        viewModel.isUpdated.observe(viewLifecycleOwner){
                updated ->
            if (updated){
                showSnackBarMessage(getString(RCore.string.result_updated))
                navigateUp()
            }
        }
        viewModel.isDeleted.observe(viewLifecycleOwner){
                deleted ->
            if (deleted){
                showSnackBarMessage(getString(RCore.string.result_deleted))
                navigateUp()
            }
        }
    }


    /**Result handler for storage permission. If permission is granted it calls
     * galleryLauncher()*/
    private val storagePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted: Boolean ->
        if (isGranted){
            galleryLauncher()
        } else{
            showSnackBarMessage(getString(RCore.string.error_permission_is_not_granted))
        }
    }

    /**Method request gallery, previously checked that permission for this purpose is granted.
     * Otherwise will request permission*/
    private fun galleryLauncher(){
        when{
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                val galleryIntent = Intent(
                    Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                galleryResultLauncher.launch(galleryIntent)
            }
            else -> {
                storagePermissionLauncher
                    .launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    /**Method handles result of galleryLauncher.
     * If RESULT_OK, it will load bitmap to corresponding UI element and
     * saves bitmap in internal storage of app*/
    private val galleryResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){ result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let {
                    val tmpBitmap: Bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        ImageDecoder.decodeBitmap(
                            ImageDecoder.createSource(requireContext().contentResolver, it))
                    } else {
                        @Suppress("DEPRECATION")
                        MediaStore.Images.Media.getBitmap(requireContext().contentResolver, it)
                    }
                    saveImageToInternalStorage(tmpBitmap)
                }
            }
        }


    /**Method saves image to internal package storage and assigns it's imagePath in viewModel*/
    private fun saveImageToInternalStorage(bitmap: Bitmap){
        lifecycleScope.launch(Dispatchers.IO){
            val wrapper = ContextWrapper(context?.applicationContext)

            var file = wrapper.getDir(Constants.EXERCISE_IMAGE_DIRECTORY, Context.MODE_PRIVATE)
            file = File(file, "${UUID.randomUUID()}.jpg")
            runCatching {
                val stream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.flush()
                stream.close()
            }.onFailure {
                it.printStackTrace()
            }

            withContext(Dispatchers.Main){
                viewModel.setImagePath(file.absolutePath)
            }
        }
    }


    /**Method firstly request error resetting and after
     * will check that fields are not empty.
     * Otherwise show error message in corresponding field*/
    private fun isUserInputIsValid(): Boolean{
        var result = false
        val error: String = getString(RCore.string.error_empty_field)

        resetFieldsErrors()

        with(binding){
            when{
                TextUtils.isEmpty(etTitle.text) -> etTitle.error = error
                TextUtils.isEmpty(etDescription.text) -> etDescription.error = error
                else -> result = true
            }
        }

        return result
    }

    /**Next method reset errors in all available fields*/
    private fun resetFieldsErrors(){
        with(binding){
            etTitle.error = null
            etDescription.error = null
        }
    }


    /**Method handles all actions related to exercise adding/updating
     * Firstly check that all fields are filled. After, rely on purpose of user
     * request adding new exercise or updating existed
     * */
    private fun requestExerciseSaving(){
        if (isUserInputIsValid()){

            val exercise = Exercise(
                binding.etTitle.text.toString(),
                viewModel.imagePath.value.toString(),
                binding.etDescription.text.toString(),
                /*Apply current id, otherwise assign 0.
                It's possible, since ID is generated automatically when a new line is created*/
                viewModel.exerciseForUpdating.value?.id ?: 0
            )

            /*Decide whether to add or update exercise*/
            if (viewModel.isExerciseShouldBeUpdated.value == true){
                viewModel.updateExercise(exercise = exercise)
            } else{
                viewModel.addExercise(exercise = exercise)
            }
        }
    }


    /**Method navigateUp using NavController*/
    private fun navigateUp(){
        findNavController().navigateUp()
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}