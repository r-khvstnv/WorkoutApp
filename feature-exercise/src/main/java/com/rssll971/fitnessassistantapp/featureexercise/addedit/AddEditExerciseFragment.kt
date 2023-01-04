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
import com.rssll971.fitnessassistantapp.coredata.domain.model.ExerciseParam
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
            ibConfirmExerciseEditing.setOnClickListener {
                requestExerciseSaving()
            }
            btnDelete.setOnClickListener {
                viewModel.deleteExercise()
            }
            ibExerciseBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }


        viewModel.isExerciseShouldBeUpdated.observe(viewLifecycleOwner){
            isForUpdate ->
            with(binding){
                /*If exercise for updating is available,
                btn Delete will be visible and btn Create will have "Update" label*/
                if (isForUpdate){
                    btnDelete.visibility = View.VISIBLE
                } else{
                    btnDelete.visibility = View.GONE
                }
            }
        }
        viewModel.exerciseParamForUpdating.observe(viewLifecycleOwner){
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


    /**
     * Result handler for storage permission. If permission is granted it calls
     * [galleryLauncher].
     * */
    private val storagePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted: Boolean ->
        if (isGranted){
            galleryLauncher()
        } else{
            showSnackBarMessage(getString(RCore.string.error_permission_is_not_granted))
        }
    }

    /**
     * Method requests gallery, previously checked that permission for this purpose is granted.
     * Otherwise, permission will be requested using [storagePermissionLauncher]
     * */
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

    /**
     * Method handles result of [galleryLauncher].
     * If [android.app.Activity.RESULT_OK], it will call [saveImageToInternalStorage]*/
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


    /**
     * Method saves [bitmap] to the internal package storage and
     * calls [com.rssll971.fitnessassistantapp.featureexercise.addedit.AddEditExerciseViewModel.setImagePath]
     * with [java.io.File.getAbsolutePath] as param.
     * */
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


    /**
     * Method firstly request [resetFieldsErrors] and after
     * will check that fields are not empty.
     * Otherwise, will be shown an error message in the corresponding field.
     * */
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

    /**
     * Method resets errors in all available fields.
     * */
    private fun resetFieldsErrors(){
        with(binding){
            etTitle.error = null
            etDescription.error = null
        }
    }


    /**
     * Method handles all actions related to Exercise record adding/updating.
     * Firstly will be checked that all fields are filled.
     *
     * After, rely on
     * [com.rssll971.fitnessassistantapp.featureexercise.addedit.AddEditExerciseViewModel.isExerciseShouldBeUpdated]
     * param, will be called [com.rssll971.fitnessassistantapp.featureexercise.addedit.AddEditExerciseViewModel.updateExercise]
     * or [com.rssll971.fitnessassistantapp.featureexercise.addedit.AddEditExerciseViewModel.addExercise]
     * */
    private fun requestExerciseSaving(){
        if (isUserInputIsValid()){

            val exerciseParam = ExerciseParam(
                binding.etTitle.text.toString(),
                viewModel.imagePath.value.toString(),
                binding.etDescription.text.toString(),
                /*Apply current id, otherwise assign 0.
                It's possible, since ID is generated automatically when a new line is created*/
                viewModel.exerciseParamForUpdating.value?.id ?: 0
            )

            /*Decide whether to add or update exercise*/
            if (viewModel.isExerciseShouldBeUpdated.value == true){
                viewModel.updateExercise(exerciseParam = exerciseParam)
            } else{
                viewModel.addExercise(exerciseParam = exerciseParam)
            }
        }
    }


    /**Method navigates Up using NavController*/
    private fun navigateUp(){
        findNavController().navigateUp()
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}