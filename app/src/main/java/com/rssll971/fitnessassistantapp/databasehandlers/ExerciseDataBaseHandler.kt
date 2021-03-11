package com.rssll971.fitnessassistantapp.databasehandlers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.rssll971.fitnessassistantapp.modelclasses.ExerciseModelClass

class ExerciseDataBaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "UserExercises"
        private const val DATABASE_VERSION = 1
        private const val TABLE_EXERCISES = "Exercises"

        private const val KEY_ID = "_id"
        private const val KEY_NAME = "name"
        private const val KEY_IMAGE_PATH = "imagePath"
        private const val KEY_DESCRIPTION = "description"
    }

    /**
     * Next method create new database with our fields
     */
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_EXERCISE_TABLE = ("CREATE TABLE $TABLE_EXERCISES($KEY_ID INTEGER PRIMARY KEY, " +
                "$KEY_NAME TEXT, $KEY_IMAGE_PATH TEXT, $KEY_DESCRIPTION TEXT)")
        db?.execSQL(CREATE_EXERCISE_TABLE)
    }

    /**
     * Next method upgrade data in data base, while new data has appeared
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_EXERCISES")
    }

    /**
     * Next method add new object of exercise history in database
     */
    fun addUsersExercise(exerciseModel: ExerciseModelClass): Long{
        //get database for write data
        val db = this.writableDatabase
        //get constructor for database values
        val contentValues = ContentValues()
        //add data in constructor
        contentValues.put(KEY_NAME, exerciseModel.getName())
        contentValues.put(KEY_IMAGE_PATH, exerciseModel.getImagePath())
        contentValues.put(KEY_DESCRIPTION, exerciseModel.getDescription())
        //add data in database
        val result = db.insert(TABLE_EXERCISES, null, contentValues)
        //close db
        db.close()
        return result
    }

    /**
     * Next method update object of exercise history in database
     */
    fun updateUserExercise(exerciseModel: ExerciseModelClass): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        //add data in constructor
        contentValues.put(KEY_NAME, exerciseModel.getName())
        contentValues.put(KEY_IMAGE_PATH, exerciseModel.getImagePath())
        contentValues.put(KEY_DESCRIPTION, exerciseModel.getDescription())

        val result = db.update(
            TABLE_EXERCISES, contentValues,
            "$KEY_ID = ${exerciseModel.getId()}", null)
        db.close()
        return result
    }

    /**
     * Next method get all existing data in db
     */
    fun viewUsersExercises(): ArrayList<ExerciseModelClass>{
        val emcList: ArrayList<ExerciseModelClass> = ArrayList()
        //select element from db
        val selectQuery = "SELECT * FROM $TABLE_EXERCISES"
        //get db for reading
        val db = this.readableDatabase
        val cursor: Cursor?
        //try to get data
        try {
            cursor = db.rawQuery(selectQuery, null)
        }
        catch (e: SQLException){
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var name: String
        var imagePath: String
        var description: String

        if (cursor.moveToNext()){
            /** Extract data until columns end*/
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                imagePath = cursor.getString(cursor.getColumnIndex(KEY_IMAGE_PATH))
                description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))

                //save data in list
                val exerciseModel = ExerciseModelClass(_id = id, _name = name,
                    _imagePath = imagePath, _description = description, _isFinished = false)
                emcList.add(exerciseModel)
            }while (cursor.moveToNext())
        }

        return  emcList
    }

    /**
     * Next method delete any user row
     */
    fun deleteUsersExercise(exerciseModel: ExerciseModelClass): Int{
        //get db
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, exerciseModel.getId())

        //delete row
        val result = db.delete(TABLE_EXERCISES, "$KEY_ID = ${exerciseModel.getId()}", null)
        db.close()
        return result
    }

    /**
     * Next method delete all user rows
     */
    fun deleteAllUserExercises(){
        val db = this.writableDatabase
        db!!.execSQL("DELETE FROM $TABLE_EXERCISES")
        db.close()
    }
}