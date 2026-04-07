package com.radjamahesaw0054.assesment1.data.local.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.radjamahesaw0054.assesment1.data.local.entity.SleepLog

class SleepDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "SleepDebt.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "sleep_logs"
        const val COLUMN_ID = "id"
        const val COLUMN_DATE = "date_millis"
        const val COLUMN_DURATION = "duration"
        const val COLUMN_QUALITY = "quality"
        const val COLUMN_DEBT = "debt"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_DATE INTEGER, "
                + "$COLUMN_DURATION REAL, "
                + "$COLUMN_QUALITY TEXT, "
                + "$COLUMN_DEBT REAL)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertLog(log: SleepLog): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DATE, log.dateMillis)
            put(COLUMN_DURATION, log.sleepDuration)
            put(COLUMN_QUALITY, log.sleepQuality)
            put(COLUMN_DEBT, log.sleepDebt)
        }
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result
    }

    fun getAllLogs(): List<SleepLog> {
        val list = mutableListOf<SleepLog>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_DATE DESC", null)

        if (cursor.moveToFirst()) {
            do {
                val log = SleepLog(
                    cursor.getInt(0),
                    cursor.getLong(1),
                    cursor.getDouble(2),
                    cursor.getString(3),
                    cursor.getDouble(4)
                )
                list.add(log)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }
}