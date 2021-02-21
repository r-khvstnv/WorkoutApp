package com.rssll971.fitnessassistantapp

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.rssll971.fitnessassistantapp.databinding.ActivityActivitiesCatalogBinding
import java.io.*

class ActivitiesCatalogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityActivitiesCatalogBinding
    private lateinit var userExercisesAdapter: UserExercisesAdapter
    private lateinit var ivExerciseImageView: ImageView
    private lateinit var imagePath: String
    //ads
    private lateinit var adViewBannerBottom: AdView
    //permissions
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

        //show list of users exercises
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

    private fun editUserExerciseRecord(exerciseModel: ExerciseModelClass){
        val dataBaseHandler = ExerciseDataBaseHandler(this)
        dataBaseHandler.updateUserExercise(exerciseModel)
        setupRecyclerView()
    }


    //delete all exercises
    private fun deleteAllExercises(){
        val dataBaseHandler = ExerciseDataBaseHandler(this)
        dataBaseHandler.deleteAllUserExercises()
        setupRecyclerView()
    }
    //delete record
    private fun deleteUserExercises(exerciseModel: ExerciseModelClass){
        val dataBaseHandler = ExerciseDataBaseHandler(this)
        dataBaseHandler.deleteUsersExercise(exerciseModel)
        setupRecyclerView()
    }

    //rv with history
    private fun setupRecyclerView(){
        binding.rvActivities.layoutManager = LinearLayoutManager(this)
        userExercisesAdapter = UserExercisesAdapter(this, getItemsUserExerciseList())
        binding.rvActivities.adapter = userExercisesAdapter

    }

    /**
     * Next method get item data for database
     */
    private fun getItemsUserExerciseList() : ArrayList<ExerciseModelClass>{
        val dataBaseHandler = ExerciseDataBaseHandler(this)
        val emcList: ArrayList<ExerciseModelClass> = dataBaseHandler.viewUsersExercises()
        return emcList
    }


    //show dialog
    fun showUserExerciseDialog(isNewExercise: Boolean, exerciseModel: ExerciseModelClass){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_create_exercise)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val etExerciseName = dialog.findViewById<EditText>(R.id.et_edit_exercise_name)
        ivExerciseImageView = dialog.findViewById<ImageView>(R.id.iv_edit_exercise_image)
        val etDescription = dialog.findViewById<EditText>(R.id.et_edit_description)
        val llSave = dialog.findViewById<LinearLayout>(R.id.ll_save)
        val llDelete = dialog.findViewById<LinearLayout>(R.id.ll_delete)

        //set image path by default like none
        imagePath = getString(R.string.st_empty_path)

        //fill data when user edit existed exercise
        if (!isNewExercise){
            etExerciseName.setText(exerciseModel.getName())
            etDescription.setText(exerciseModel.getDescription())

            //image
            if (exerciseModel.getImagePath() != getString(R.string.st_empty_path)){
                imagePath = exerciseModel.getImagePath()
                val imageFile = File(imagePath)
                //targetSize
                Glide.with(this).load(Uri.fromFile(imageFile)).fitCenter().into(ivExerciseImageView)
            }
        }
        etExerciseName.setText(exerciseModel.getName())
        etDescription.setText(exerciseModel.getDescription())
        dialog.show()



        ivExerciseImageView.setOnClickListener {
            if (isPermissionsAreAllowed()){
                val pickImageIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(pickImageIntent, GALLERY_CODE)
            }
            else{
                requestStoragePermission()
            }

        }

        llSave.setOnClickListener {
            if (etExerciseName.text.isEmpty() && etDescription.text.isEmpty()){
                //todo make string
                Toast.makeText(this, "Add data",
                    Toast.LENGTH_LONG).show()
            }
            else{
                val name = etExerciseName.text.toString()
                val description = etDescription.text.toString()
                if (isNewExercise){
                    addUserExerciseRecord(
                        ExerciseModelClass(0, name, imagePath, description, false))
                }
                else{
                    editUserExerciseRecord(
                        ExerciseModelClass(exerciseModel.getId(), name, imagePath, description, false))
                }
                dialog.dismiss()
            }

        }
        llDelete.setOnClickListener {
            if (!isNewExercise){
                deleteUserExercises(exerciseModel)
                dialog.dismiss()
            }
        }
    }


    //request permission
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
                //todo string
                Toast.makeText(this,
                    "Access to the storage is not available.\n\nPlease grant permission",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
    /**
     * Next fun check permissions availability for app
     * */
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
     * Next fun extracts user image from gallery and substitutes data in image view
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == GALLERY_CODE){
                //get user image as background, using exception
                try {
                    //check for available data
                    if (data!!.data != null){
                        //ONLY set image into view

                        //new method
                        //get uri
                        val imageUri = data.data
                        //convert uri to stream
                        val imageStream: InputStream = applicationContext.contentResolver.openInputStream(imageUri!!)!!
                        //convert stream to bitmap
                        val selectedImage: Bitmap = BitmapFactory.decodeStream(imageStream)
                        imageStream.close()
                        val result = saveImage(selectedImage)
                        //targetSize
                        Glide.with(this).load(File(result)).fitCenter().into(ivExerciseImageView)
                        //save path for db
                        imagePath = result
                        //todo delete
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show()



                    }
                    //if something goes wrong
                    else{
                        //todo make string
                        Toast.makeText(this, "Wrong data type",
                            Toast.LENGTH_LONG).show()
                    }
                }
                catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }

    //copy image in app directory
    private fun saveImage(bitmap: Bitmap): String{
        var result = ""
        try {
            //variable where we will save our output data
            val bytes = ByteArrayOutputStream()
            //compress our bitmap to PNG using stream of val bytes
            //todo change quality
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