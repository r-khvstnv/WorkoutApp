package com.rssll971.fitnessassistantapp.databasehandlers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.rssll971.fitnessassistantapp.models.BmiHistoryModel

class BmiDataBaseHandler(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "BmiHistory"
        private const val DATABASE_VERSION = 1
        private const val TABLE_BMI_DATES = "BmiDates"

        private const val KEY_ID = "_id"
        private const val KEY_DATE = "date"
        private const val KEY_WEIGHT = "weight"
        private const val KEY_HEIGHT = "height"
        private const val KEY_BMI_INDEX = "bmiIndex"
    }

    /**
     * Next method creates new database with corresponding fields
     */
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_BMI_DATES_TABLE =
            ("CREATE TABLE $TABLE_BMI_DATES($KEY_ID INTEGER PRIMARY KEY, " +
                "$KEY_DATE TEXT, $KEY_WEIGHT REAL, $KEY_HEIGHT REAL, $KEY_BMI_INDEX REAL)")
        db?.execSQL(CREATE_BMI_DATES_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_BMI_DATES")
    }

    /**
     * Next method adds new object of bmi history in database
     */
    fun addCurrentBmiResult(bmiHistoryModel: BmiHistoryModel): Long{
        //get database for write data
        val db = this.writableDatabase
        //get constructor for database values
        val contentValues = ContentValues()
        //add data in constructor
        contentValues.put(KEY_DATE, bmiHistoryModel.date)
        contentValues.put(KEY_WEIGHT, bmiHistoryModel.weight)
        contentValues.put(KEY_HEIGHT, bmiHistoryModel.height)
        contentValues.put(KEY_BMI_INDEX, bmiHistoryModel.bmiIndex)
        //add data in database
        val result = db.insert(TABLE_BMI_DATES, null, contentValues)
        //close db
        db.close()
        return result
    }

    /**
     * Next method gets all existing data in db
     */
    fun viewBmiResult(): ArrayList<BmiHistoryModel>{
        val bhmList: ArrayList<BmiHistoryModel> = ArrayList()
        //select element from db
        val selectQuery = "SELECT * FROM $TABLE_BMI_DATES"
        //get db for reading
        val db = this.readableDatabase
        val cursor: Cursor?
        //try to get data
        try {
            cursor = db.rawQuery(selectQuery, null)
        }
        catch (e: SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var date: String
        var weight: Float
        var height: Float
        var bmiIndex: Float

        if (cursor.moveToFirst()){
            /** Extract data until columns end*/
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                date = cursor.getString(cursor.getColumnIndex(KEY_DATE))
                weight = cursor.getFloat(cursor.getColumnIndex(KEY_WEIGHT))
                height = cursor.getFloat(cursor.getColumnIndex(KEY_HEIGHT))
                bmiIndex = cursor.getFloat(cursor.getColumnIndex(KEY_BMI_INDEX))

                //save data in list
                val bmiHistoryModel = BmiHistoryModel(
                    id = id, date = date, weight = weight,
                    height = height, bmiIndex = bmiIndex
                )
                bhmList.add(bmiHistoryModel)
            }while (cursor.moveToNext())
        }

        cursor.close()
        return bhmList
    }

    /**
     * Next method deletes all columns in db
     */
    fun eraseAll(){
        val db = this.writableDatabase
        db!!.execSQL("DELETE FROM $TABLE_BMI_DATES")
        db.close()
    }
}