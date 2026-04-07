package com.radjamahesaw0054.assesment1.data.local.dao

import android.content.ContentValues
import android.database.Cursor
import com.radjamahesaw0054.assesment1.data.local.database.SleepDatabase
import com.radjamahesaw0054.assesment1.data.local.entity.SleepLog


class SleepDao(private val dbHelper: SleepDatabase) {

    fun insert(log: SleepLog): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(SleepDatabase.COLUMN_DATE, log.dateMillis)
            put(SleepDatabase.COLUMN_DURATION, log.sleepDuration)
            put(SleepDatabase.COLUMN_QUALITY, log.sleepQuality)
            put(SleepDatabase.COLUMN_DEBT, log.sleepDebt)
        }
        return db.insert(SleepDatabase.TABLE_NAME, null, values)
    }

    fun getAllHistory(): List<SleepLog> {
        val logs = mutableListOf<SleepLog>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM ${SleepDatabase.TABLE_NAME} ORDER BY ${SleepDatabase.COLUMN_DATE} DESC",
            null
        )

        parseCursor(cursor, logs)
        return logs
    }

    fun getLastSevenDays(): List<SleepLog> {
        val logs = mutableListOf<SleepLog>()
        val db = dbHelper.readableDatabase

        val sevenDaysAgo = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000)

        val cursor = db.rawQuery(
            "SELECT * FROM ${SleepDatabase.TABLE_NAME} WHERE ${SleepDatabase.COLUMN_DATE} >= ? ORDER BY ${SleepDatabase.COLUMN_DATE} ASC",
            arrayOf(sevenDaysAgo.toString())
        )

        parseCursor(cursor, logs)
        return logs
    }

    fun getTotalDebt(): Double {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT SUM(${SleepDatabase.COLUMN_DEBT}) FROM ${SleepDatabase.TABLE_NAME}", null)
        var total = 0.0
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0)
        }
        cursor.close()
        return total
    }

    private fun parseCursor(cursor: Cursor, list: MutableList<SleepLog>) {
        if (cursor.moveToFirst()) {
            do {
                list.add(SleepLog(
                    id = cursor.getInt(0),
                    dateMillis = cursor.getLong(1),
                    sleepDuration = cursor.getDouble(2),
                    sleepQuality = cursor.getString(3),
                    sleepDebt = cursor.getDouble(4)
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
    }
}