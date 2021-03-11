package com.rssll971.fitnessassistantapp.databasehandlers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.rssll971.fitnessassistantapp.modelclasses.WorkoutStatisticModelClass

class WorkoutStatisticDataBaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "WorkoutStatistic"
        private const val DATABASE_VERSION = 1
        private const val TABLE_STATISTIC = "Statistic"

        private const val KEY_ID = "_id"
        private const val KEY_DATE = "date"
        private const val KEY_REST_DURATION = "restDuration"
        private const val KEY_EXERCISE_DURATION = "exerciseDuration"
        private const val KEY_EXERCISE_AMOUNT = "exerciseAmount"
    }

    /**
     * Next method create new database with our fields
     */
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_EXERCISE_TABLE = ("CREATE TABLE $TABLE_STATISTIC($KEY_ID INTEGER PRIMARY KEY, $KEY_DATE TEXT, " +
                "$KEY_REST_DURATION INTEGER, $KEY_EXERCISE_DURATION INTEGER, $KEY_EXERCISE_AMOUNT INTEGER)")
        db?.execSQL(CREATE_EXERCISE_TABLE)
    }

    /**
     * Next method upgrade data in data base, while new data has appeared
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_STATISTIC")
    }

    /**
     * Next method add new object of exercise history in database
     */
    fun addStatisticData(statisticData: WorkoutStatisticModelClass): Long{
        //get database for write data
        val db = this.writableDatabase
        //get constructor for database values
        val contentValues = ContentValues()
        //add data in constructor
        contentValues.put(KEY_DATE, statisticData.getDate())
        contentValues.put(KEY_REST_DURATION, statisticData.getRestDuration())
        contentValues.put(KEY_EXERCISE_DURATION, statisticData.getExerciseDuration())
        contentValues.put(KEY_EXERCISE_AMOUNT, statisticData.getExerciseAmount())
        //add data in database
        val result = db.insert(TABLE_STATISTIC, null, contentValues)
        //close db
        db.close()
        return result
    }

    /**
     * Next method get all existing data in db
     */
    fun viewStatisticData(): ArrayList<WorkoutStatisticModelClass>{
        val statisticList: ArrayList<WorkoutStatisticModelClass> = ArrayList()
        //select elements from db
        val selectQuery = "SELECT * FROM $TABLE_STATISTIC"
        //get db for reading
        val db = this.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        }
        catch (e: SQLException){
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var date: String
        var restDuration: Int
        var exerciseDuration: Int
        var exerciseAmount: Int

        if (cursor.moveToNext()){
            /** Extract data until columns end*/
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                date = cursor.getString(cursor.getColumnIndex(KEY_DATE))
                restDuration = cursor.getInt(cursor.getColumnIndex(KEY_REST_DURATION))
                exerciseDuration = cursor.getInt(cursor.getColumnIndex(KEY_EXERCISE_DURATION))
                exerciseAmount = cursor.getInt(cursor.getColumnIndex(KEY_EXERCISE_AMOUNT))

                //save data in the list
                val workoutStatisticModel = WorkoutStatisticModelClass(_id = id, _date = date,
                    _restDuration = restDuration, _exerciseDuration = exerciseDuration,
                    _exerciseAmount = exerciseAmount)
                statisticList.add(workoutStatisticModel)
            }while (cursor.moveToNext())
        }

        return statisticList
    }
}