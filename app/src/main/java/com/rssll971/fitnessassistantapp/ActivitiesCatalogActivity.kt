package com.rssll971.fitnessassistantapp

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.ColorDrawable
import android.media.ExifInterface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.rssll971.fitnessassistantapp.databinding.ActivityActivitiesCatalogBinding
import org.w3c.dom.Text
import java.io.*

class ActivitiesCatalogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityActivitiesCatalogBinding
    //adapter for user exercises
    private lateinit var userExercisesAdapter: UserExercisesAdapter
    //image view with for user image, need for glide
    private lateinit var ivExerciseImageView: ImageView
    //path for user image, need for editing
    private lateinit var imagePath: String
    //ads
    private lateinit var adViewBannerBottom: AdView

    /**
     * Permissions
     */
    companion object{
        private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
        private const val GALLERY_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivitiesCatalogBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /** show list of users exercises*/
        setupRecyclerView()

        /** Ads*/
        MobileAds.initialize(this)
        adViewBannerBottom = findViewById(R.id.adView_banner_activities_catalog_bottom)
        adViewBannerBottom.loadAd(AdRequest.Builder().build())
        adViewBannerBottom.adListener = object : AdListener(){
            override fun onAdClosed() {
                adViewBannerBottom.loadAd(AdRequest.Builder().build())
            }
        }
        /** All clickable*/
        //add exercise
        binding.llAddActivities.setOnClickListener {
            showUserExerciseDialog(true,
                    ExerciseModelClass(0, "", getString(R.string.st_empty_path), "", false)
            )
        }
        //delete all
        binding.llDeleteExercises.setOnClickListener {
            deleteAllExercises()
        }

    }

    /**
     * Next method add new user exercise in database
     */
    private fun addUserExerciseRecord(exerciseModel: ExerciseModelClass){
        //get database handler
        val dataBaseHandler = ExerciseDataBaseHandler(this)
        //note: system automatically change id
        val status = dataBaseHandler.addUsersExercise(ExerciseModelClass(0, exerciseModel.getName(),
            exerciseModel.getImagePath(), exerciseModel.getDescription(), false))

        if (status > -1){
            setupRecyclerView()
        }
    }
    /**
     * Next method edit user exercise in database
     */
    private fun editUserExerciseRecord(exerciseModel: ExerciseModelClass){
        val dataBaseHandler = ExerciseDataBaseHandler(this)
        dataBaseHandler.updateUserExercise(exerciseModel)
        setupRecyclerView()
    }
    /**
     * Next method delete all user exercise in database
     */
    private fun deleteAllExercises(){
        val dataBaseHandler = ExerciseDataBaseHandler(this)
        dataBaseHandler.deleteAllUserExercises()
        setupRecyclerView()
    }
    /**
     * Next method delete chosen user exercise in database
     */
    private fun deleteUserExercises(exerciseModel: ExerciseModelClass){
        val dataBaseHandler = ExerciseDataBaseHandler(this)
        dataBaseHandler.deleteUsersExercise(exerciseModel)
        setupRecyclerView()
    }

    /**
     * Next method run RecyclerView with all user exercises
     */
    private fun setupRecyclerView(){
        binding.rvActivities.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        userExercisesAdapter = UserExercisesAdapter(this, getItemsUserExerciseList())
        binding.rvActivities.adapter = userExercisesAdapter
    }

    /**
     * Next method get all user exercises from database
     */
    private fun getItemsUserExerciseList() : ArrayList<ExerciseModelClass>{
        val dataBaseHandler = ExerciseDataBaseHandler(this)
        val emcList: ArrayList<ExerciseModelClass> = dataBaseHandler.viewUsersExercises()
        return emcList
    }


    /**
     * Next method show dialog for editing exercise
     *
     * Called from UserExerciseAdapter
     */
    fun showUserExerciseDialog(isNewExercise: Boolean, exerciseModel: ExerciseModelClass){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_create_exercise)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val etExerciseName = dialog.findViewById<EditText>(R.id.et_edit_exercise_name)
        ivExerciseImageView = dialog.findViewById<ImageView>(R.id.iv_edit_exercise_image)
        val etDescription = dialog.findViewById<EditText>(R.id.et_edit_description)
        val llSave = dialog.findViewById<LinearLayout>(R.id.ll_save)
        val llDelete = dialog.findViewById<LinearLayout>(R.id.ll_delete)
        val tvDescriptionSize = dialog.findViewById<TextView>(R.id.tv_description_size)

        /** Set image path by default like none.
         * As so its global var and if we don't erase previous path,
         * for new exercise will be used the last one
         */
        imagePath = getString(R.string.st_empty_path)

        /** fill data when user edit existed exercise*/
        if (!isNewExercise){
            etExerciseName.setText(exerciseModel.getName())
            etDescription.setText(exerciseModel.getDescription())
            //image
            if (exerciseModel.getImagePath() != getString(R.string.st_empty_path)){
                //targetSize
                Glide.with(this).load(exerciseModel.getImagePath()).fitCenter().into(ivExerciseImageView)
            }
        }


        dialog.show()

        //add image for exercise
        ivExerciseImageView.setOnClickListener {
            if (isPermissionsAreAllowed()){
                val pickImageIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(pickImageIntent, GALLERY_CODE)
            }
            else{
                requestStoragePermission()
            }
        }

        //show max characters for description
        etDescription.doOnTextChanged { text, start, before, count ->
            tvDescriptionSize.text = "$count /400"
        }

        //save exercise and close dialog
        llSave.setOnClickListener {
            if (etExerciseName.text.isEmpty() or etDescription.text.isEmpty()){
                Toast.makeText(this, getString(R.string.st_add_data),
                    Toast.LENGTH_LONG).show()
            }
            else{
                val name = etExerciseName.text.toString()
                val description = etDescription.text.toString()
                //for new exercise just put all in method
                if (isNewExercise){
                    addUserExerciseRecord(
                        ExerciseModelClass(0, name, imagePath, description, false))
                }
                else{//for edit exercise, firstly check is new image available. Otherwise, put previous
                    if(imagePath == getString(R.string.st_empty_path)){
                        imagePath = exerciseModel.getImagePath()
                    }


                    editUserExerciseRecord(
                        ExerciseModelClass(exerciseModel.getId(), name, imagePath, description, false))
                }
                dialog.dismiss()
            }
        }

        //delete current exercise
        llDelete.setOnClickListener {
            if (!isNewExercise){
                deleteUserExercises(exerciseModel)
                dialog.dismiss()
            }
        }
    }


    /**
     * Next method request permissions for get images
     */
    private fun requestStoragePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                PERMISSIONS_REQUIRED.toString())){
            //nothing to do, due to user reject them or granted
        }
        else{
            //request permissions
            ActivityCompat.requestPermissions(this, PERMISSIONS_REQUIRED, GALLERY_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == GALLERY_CODE){
            var allGranted = false
            //check for granted permission
            for (i in grantResults.indices){
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    allGranted = true
                }
            }

            if (!allGranted){
                Toast.makeText(this,
                    getString(R.string.st_access),
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Next method check permissions availability for app
     */
    private fun isPermissionsAreAllowed(): Boolean{
        var result: Boolean = false
        if (
            ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            &&
            ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        ){result = true}


        return result
    }
    /**
     * Next method extracts user image from gallery and substitutes data in image view
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == GALLERY_CODE){
                /**
                 * First try to get image. After that copy it to app folder and send new path
                 */
                try {
                    //check for available data
                    if (data!!.data != null){


                        /** Next lines get all needed info to prevent portrait image from rotation */
                        val options = BitmapFactory.Options()
                        options.inJustDecodeBounds = false
                        //get uri
                        val imageUri = data.data
                        //get real image path
                        val realPath = getRealPathFromUri(imageUri!!)
                        //convert uri to stream
                        val imageStream: InputStream = applicationContext.contentResolver.openInputStream(imageUri)!!
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
                        Toast.makeText(this, getString(R.string.st_wrong_data),
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
        val cursor = contentResolver.query(uri, null, null, null, null)
        if (cursor == null){
            result = uri.path!!
        }
        else{
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
        val orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        when(orientation){
            ExifInterface.ORIENTATION_ROTATE_90 -> return rotateImage(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> return rotateImage(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> return rotateImage(bitmap, 270)
            else -> return bitmap
        }
    }
    /**
     * Next method rotate image
     */
    private fun rotateImage(bitmap: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedImage = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        bitmap.recycle()
        return rotatedImage
    }

    /**
     * Next method save image in app directory and return new path
     */
    private fun saveImage(bitmap: Bitmap): String{
        var result = ""
        try {
            val bytes = ByteArrayOutputStream()
            //compress our bitmap to JPEG using stream of val bytes
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            //make it as single file
            //external directory -> as absolute file -> separate ->
            val myFile = File(externalCacheDir!!.absoluteFile.toString()
                    + File.separator + "UsersImage" + System.currentTimeMillis()/1000 + ".jpeg")
            //stream of our file
            val myFileOS = FileOutputStream(myFile)
            //start writing
            myFileOS.write(bytes.toByteArray())
            //close os write operation
            myFileOS.close()
            //store the result to path
            result = myFile.absolutePath
        }
        catch (e: Exception){
            result = ""
            e.printStackTrace()
        }

        return result
    }
}