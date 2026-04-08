package com.radjamahesaw0054.assesment1.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.radjamahesaw0054.assesment1.data.local.entity.SleepLog
import com.radjamahesaw0054.assesment1.repository.SleepRepository

class SleepViewModel(private val repository: SleepRepository) : ViewModel() {

    var historyList by mutableStateOf(repository.fetchAllHistory())
        private set

    fun saveData(duration: Double, quality: String) {
        val targetHours = 8.0
        val debt = (targetHours - duration).coerceAtLeast(0.0)
        val newEntry = SleepLog(
            dateMillis = System.currentTimeMillis(),
            sleepDuration = duration,
            sleepQuality = quality,
            sleepDebt = debt
        )
        repository.saveSleepEntry(newEntry)
        refreshData()
    }

    fun refreshData() {
        historyList = repository.fetchAllHistory()
    }

    fun getTotalDebt(): Double {
        return repository.checkTotalDebt()
    }
}