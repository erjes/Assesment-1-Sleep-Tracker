package com.radjamahesaw0054.assesment1.repository

import com.radjamahesaw0054.assesment1.data.local.dao.SleepDao
import com.radjamahesaw0054.assesment1.data.local.entity.SleepLog

class SleepRepository(private val dao: SleepDao) {
    fun saveSleepEntry(log: SleepLog) = dao.insert(log)
    fun fetchAllHistory() = dao.getAllHistory()
    fun checkTotalDebt() = dao.getTotalDebt()
}