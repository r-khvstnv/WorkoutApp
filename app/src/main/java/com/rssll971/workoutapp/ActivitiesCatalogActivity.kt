package com.rssll971.workoutapp

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.rssll971.workoutapp.databinding.ActivityActivitiesCatalogBinding
import java.util.concurrent.TimeUnit

class ActivitiesCatalogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityActivitiesCatalogBinding
    private lateinit var userExercisesAdapter: UserExercisesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivitiesCatalogBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //show list of users exercises
        setupRecyclerView()

        binding.llAddActivities.setOnClickListener {
            showUserExerciseDialog(true,
                    ExerciseModelClass(0, "", "none", "", false)
            )
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

    //todo implement
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

        var etExerciseName = dialog.findViewById<EditText>(R.id.et_edit_exercise_name)
        var ivExerciseImage = dialog.findViewById<ImageView>(R.id.iv_edit_exercise_image)
        var etDescription = dialog.findViewById<EditText>(R.id.et_edit_description)
        val llSave = dialog.findViewById<LinearLayout>(R.id.ll_save)
        var llDelete = dialog.findViewById<LinearLayout>(R.id.ll_delete)

        //fill data when user edit existed exercise
        if (!isNewExercise){
            etExerciseName.setText(exerciseModel.getName())
            etDescription.setText(exerciseModel.getDescription())

            //image
            if (exerciseModel.getImagePath() != "none"){
                ivExerciseImage.setImageURI(exerciseModel.getImagePath().toUri())
            }
        }
        etExerciseName.setText(exerciseModel.getName())
        etDescription.setText(exerciseModel.getDescription())
        dialog.show()

        //todo add image picker

        llSave.setOnClickListener {
            val name = etExerciseName.text.toString()
            //todo change
            val image = "none"
            val description = etDescription.text.toString()
            if (isNewExercise){
                addUserExerciseRecord(
                    ExerciseModelClass(0, name, image, description, false))
                dialog.dismiss()
            }
            else{
                editUserExerciseRecord(
                    ExerciseModelClass(exerciseModel.getId(), name, image, description, false))
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


}