package com.rssll971.fitnessassistantapp.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.ColorDrawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.rssll971.fitnessassistantapp.ExerciseAdapterCallback
import com.rssll971.fitnessassistantapp.databasehandlers.ExerciseDataBaseHandler
import com.rssll971.fitnessassistantapp.models.ExerciseModel
import com.rssll971.fitnessassistantapp.R
import com.rssll971.fitnessassistantapp.adapters.UserExercisesAdapter
import com.rssll971.fitnessassistantapp.activities.MainActivity
import com.rssll971.fitnessassistantapp.databinding.DialogCreateExerciseBinding
import com.rssll971.fitnessassistantapp.databinding.FragmentExerciseCatalogBinding
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("UseRequireInsteadOfGet")
class ExerciseCatalogFragment : Fragment() {
    private var _binding: FragmentExerciseCatalogBinding? = null
    private val binding get() = _binding!!
    //adapter for user exercises
    private lateinit var userExercisesAdapter: UserExercisesAdapter
    //image view with for user image, need for glide
    private lateinit var ivExerciseImageView: ImageView
    //path for user image, need for editing
    private lateinit var imagePath: String

    /**
     * Codes
     */
    companion object{
        const val GALLERY_CODE = 101
        private const val IMAGE_DIRECTORY = "FAImages"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentExerciseCatalogBinding.inflate(inflater, container, false)

        /** show list of users exercises*/
        setupRecyclerView()


        binding.llAddActivities.setOnClickListener {
            showUserExerciseDialog(false, ExerciseModel())
        }
        binding.llDeleteExercises.setOnClickListener {
            deleteAllExercises()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Next method adds new user exercise in database
     */
    private fun addUserExerciseRecord(exerciseModel: ExerciseModel){
        //get database handler
        val dataBaseHandler by lazy { ExerciseDataBaseHandler(context!! as MainActivity) }
        //note: system automatically change id
        val status = dataBaseHandler.addUsersExercise(
            ExerciseModel(0, exerciseModel.name,
            exerciseModel.imagePath, exerciseModel.description, false)
        )

        if (status > -1){
            setupRecyclerView()
        }
    }
    /**
     * Next method edits user exercise in database
     */
    private fun editUserExerciseRecord(exerciseModel: ExerciseModel){
        val dataBaseHandler by lazy { ExerciseDataBaseHandler(context!! as MainActivity) }
        dataBaseHandler.updateUserExercise(exerciseModel)
        setupRecyclerView()
    }
    /**
     * Next method deletes all user exercise in database
     */
    private fun deleteAllExercises(){
        val dataBaseHandler by lazy { ExerciseDataBaseHandler(context!! as MainActivity) }
        dataBaseHandler.deleteAllUserExercises()
        setupRecyclerView()
    }
    /**
     * Next method deletes chosen user exercise in database
     */
    private fun deleteUserExercises(exerciseModel: ExerciseModel){
        val dataBaseHandler by lazy { ExerciseDataBaseHandler(context!! as MainActivity) }
        dataBaseHandler.deleteUsersExercise(exerciseModel)
        setupRecyclerView()
    }
    /**
     * Next method shows RecyclerView with all user exercises
     */
    private fun setupRecyclerView(){
        binding.rvActivities.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        userExercisesAdapter =
            UserExercisesAdapter(
                requireContext(),
                getItemsUserExerciseList(),
                this,
                object : ExerciseAdapterCallback{
                override fun onItemSelected(position: Int, isNeededAdd: Boolean) {}

                override fun onItemEditing(exerciseModel: ExerciseModel, isExisted: Boolean) {
                    showUserExerciseDialog(isExisted, exerciseModel)
                }

            })
        binding.rvActivities.adapter = userExercisesAdapter
    }

    /**
     * Next method gets all user exercises from database
     */
    private fun getItemsUserExerciseList() : ArrayList<ExerciseModel>{
        val dataBaseHandler by lazy { ExerciseDataBaseHandler(context!! as MainActivity) }
        return dataBaseHandler.viewUsersExercises()
    }


    /**
     * Next method shows dialog for editing/creating new exercise
     */
    @SuppressLint("SetTextI18n")
    private fun showUserExerciseDialog(isExisted: Boolean, exerciseModel: ExerciseModel){
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogCreateExerciseBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        ivExerciseImageView = dialog.findViewById(R.id.iv_edit_exercise_image)

        /** Set image path by default like none.
         * As so its global var and if we don't erase previous path,
         * for new exercise will be used the last one
         */
        imagePath = getString(R.string.st_empty_path)

        /** fill data when user edit existed exercise*/
        if (isExisted){
            dialogBinding.etEditExerciseName.setText(exerciseModel.name)
            dialogBinding.etEditDescription.setText(exerciseModel.description)
            //image
            if (exerciseModel.imagePath != getString(R.string.st_empty_path)){
                //targetSize
                Glide.with(this)
                    .load(exerciseModel.imagePath)
                    .fitCenter().into(ivExerciseImageView)
            }
        }

        dialog.show()
        //add image for exercise
        ivExerciseImageView.setOnClickListener {
            getImageFromGallery()
        }
        //show max characters for description
        dialogBinding.etEditDescription.doOnTextChanged { _, _, _, count ->
            dialogBinding.tvDescriptionSize.text = "$count /400"
        }
        //save exercise and close dialog
        dialogBinding.llSave.setOnClickListener {
            if (dialogBinding.etEditExerciseName.text.isEmpty()
                || dialogBinding.etEditDescription.text.isEmpty()){

                Toast.makeText(requireContext(), getString(R.string.st_add_data),
                    Toast.LENGTH_LONG).show()
            } else{
                val name = dialogBinding.etEditExerciseName.text.toString()
                val description = dialogBinding.etEditDescription.text.toString()
                //for new exercise just put all in method
                if (!isExisted){
                    addUserExerciseRecord(
                        ExerciseModel(
                            0,
                            name,
                            imagePath,
                            description,
                            false)
                    )
                } else{
                    //for edit exercise, firstly check is new image available.
                        // Otherwise, put previous
                    if(imagePath == getString(R.string.st_empty_path))
                        imagePath = exerciseModel.imagePath

                    editUserExerciseRecord(
                        ExerciseModel(
                            exerciseModel.id,
                            name,
                            imagePath,
                            description,
                            false)
                    )
                }
                dialog.dismiss()
            }
        }

        //delete current exercise
        dialogBinding.llDelete.setOnClickListener {
            if (isExisted){
                deleteUserExercises(exerciseModel)
                dialog.dismiss()
            }
        }
    }


    /**
     * Next dialog will be shown, if previously user reject all permissions
     * required to gallery & share features
     */
    private fun showRationalPermissionDialog(){
        val permissionAlertDialog =
            AlertDialog.Builder(requireContext()).setMessage(R.string.st_permission_needed_to_be_granted)
        //positive button
        permissionAlertDialog.setPositiveButton(getString(R.string.st_go_to_settings)){ _: DialogInterface, _: Int ->
            try {
                //move to app settings
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", activity?.packageName, null)
                intent.data = uri
                startActivity(intent)
            } catch (e: ActivityNotFoundException){
                e.printStackTrace()
            }
        }
        //negative button
        permissionAlertDialog.setNegativeButton(R.string.st_cancel){
                dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        //show
        permissionAlertDialog.show()
    }

    /**
     * Next method requests permissions for get images
     */
    private fun getImageFromGallery(){
        Dexter.withContext(requireContext())
            .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : MultiplePermissionsListener{
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                if (report!!.areAllPermissionsGranted()){
                    val pickImageIntent = Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(pickImageIntent, GALLERY_CODE)
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissionsList: MutableList<PermissionRequest>?,
                permissionToken: PermissionToken?
            ) {
                showRationalPermissionDialog()
            }

        }).onSameThread().check()
    }


    /**
     * Next method extracts user image from gallery and substitutes data in image view
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == GALLERY_CODE){
                /**
                 * First try to get image.
                 * After that copy it to app folder and return new path
                 */
                try {
                    //check for available data
                    if (data!!.data != null){

                        /** Next lines get all needed actions to prevent image from rotation */
                        val options = BitmapFactory.Options()
                        options.inJustDecodeBounds = false
                        //get uri
                        val imageUri = data.data
                        //get real image path
                        val realPath = getRealPathFromUri(imageUri!!)
                        //convert uri to stream
                        val imageStream: InputStream = activity!!.applicationContext.contentResolver.openInputStream(imageUri)!!
                        //convert stream to bitmap
                        val selectedImage: Bitmap? = BitmapFactory.decodeStream(imageStream, null, options)
                        imageStream.close()
                        //rotate image
                        val rotatedImage = rotateImageIfRequired(selectedImage!!, realPath)


                        /** Save image in app folder and return new path*/
                        val result = saveImage(rotatedImage)

                        //targetSize
                        Glide.with(this).load(result).fitCenter().into(ivExerciseImageView)
                        //save path for next sending in database
                        imagePath = result
                    }
                    //if something goes wrong
                    else{
                        Toast.makeText(requireContext(), getString(R.string.st_wrong_data),
                            Toast.LENGTH_LONG).show()
                    }
                }
                catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * Next method get real path of image. Without it app can't to rotate image and appear ENOENT error
     */
    private fun getRealPathFromUri(uri: Uri): String{
        val result: String
        val cursor =
            activity!!.contentResolver.query(uri,
                null, null, null, null)
        if (cursor == null){
            result = uri.path!!
        } else{
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }


    /**
     * Next method prepare image for rotation
     */
    private fun rotateImageIfRequired(bitmap: Bitmap, imagePath: String): Bitmap {
        val ei = ExifInterface(File(imagePath).absolutePath)
        return when(ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)){
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270)
            else -> rotateImage(bitmap, 0)
        }
    }
    /**
     * Next method rotate image
     */
    private fun rotateImage(bitmap: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        val scaleFactor: Float
        val requiredSize = 1280f
        /** Make image smaller, due to Cursor in SQLite has only 2MB capability
         *  and Improve performance
         * */
        scaleFactor = if (bitmap.width > bitmap.height){
            (requiredSize / bitmap.width)
        } else{
            (requiredSize / bitmap.height)
        }

        matrix.postRotate(degree.toFloat())
        matrix.postScale(scaleFactor, scaleFactor)

        val rotatedImage = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        bitmap.recycle()
        return rotatedImage
    }

    /**
     * Next method save image in app directory and return new path
     */
    private fun saveImage(bitmap: Bitmap): String{
        var result = ""

        val wrapper = ContextWrapper(activity!!.applicationContext)
        //set image location in internal storage
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        //store in jpeg format
        file = File(file, "${UUID.randomUUID()}.jpeg")
        result = file.absolutePath
        Log.i("Size", "${file.length() / 1024}")
        try {
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        }catch (e: IOException){
            e.printStackTrace()
        }
        return result
    }
}