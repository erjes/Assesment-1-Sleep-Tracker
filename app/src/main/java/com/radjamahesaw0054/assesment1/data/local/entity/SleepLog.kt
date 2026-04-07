package com.radjamahesaw0054.assesment1.data.local.entity

data class SleepLog(
    val id: Int = 0,
    val dateMillis: Long,
    val sleepDuration: Double,
    val sleepQuality: String,
    val sleepDebt: Double
)