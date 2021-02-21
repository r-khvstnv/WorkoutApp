package com.rssll971.fitnessassistantapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class BmiDataBaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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
     * Next method create new database with our fields
     */
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_BMI_DATES_TABLE = ("CREATE TABLE $TABLE_BMI_DATES($KEY_ID INTEGER PRIMARY KEY, " +
                "$KEY_DATE TEXT, $KEY_WEIGHT REAL, $KEY_HEIGHT REAL, $KEY_BMI_INDEX REAL)")
        db?.execSQL(CREATE_BMI_DATES_TABLE)
    }

    /**
     * Next method upgrade data in data base, while new data has appeared
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_BMI_DATES")
    }

    /**
     * Next method add new object of bmi history in database
     */
    fun addCurrentBmiResult(bmiHistoryModel: BmiHistoryModelClass): Long{
        //get database for write data
        val db = this.writableDatabase
        //get constructor for database values
        val contentValues = ContentValues()
        //add data in constructor
        contentValues.put(KEY_DATE, bmiHistoryModel.getDate())
        contentValues.put(KEY_WEIGHT, bmiHistoryModel.getWeight())
        contentValues.put(KEY_HEIGHT, bmiHistoryModel.getHeight())
        contentValues.put(KEY_BMI_INDEX, bmiHistoryModel.getBmiIndex())
        //add data in database
        val result = db.insert(TABLE_BMI_DATES, null, contentValues)
        //close db
        db.close()
        return result
    }

    /**
     * Next method get all existing data in db
     */
    fun viewBmiResult(): ArrayList<BmiHistoryModelClass>{
        val bhmList: ArrayList<BmiHistoryModelClass> = ArrayList()
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
                val bmiHistoryModel = BmiHistoryModelClass(
                    _id = id, _date = date, _weight = weight,
                    _height = height, _bmiIndex = bmiIndex
                )
                bhmList.add(bmiHistoryModel)
            }while (cursor.moveToNext())
        }

        return bhmList
    }

    /**
     * Next method delete all columns in db
     */
    fun eraseAll(){
        val db = this.writableDatabase
        db!!.execSQL("DELETE FROM $TABLE_BMI_DATES")
        db.close()
    }


}