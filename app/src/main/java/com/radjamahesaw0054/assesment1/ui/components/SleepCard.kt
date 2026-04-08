package com.radjamahesaw0054.assesment1.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.radjamahesaw0054.assesment1.R
import com.radjamahesaw0054.assesment1.data.local.entity.SleepLog
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SleepCard(log: SleepLog) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.history_date, formatMillisToDate(log.dateMillis)),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = stringResource(R.string.history_duration, log.sleepDuration),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = (log.sleepQuality),
                style = MaterialTheme.typography.bodyLarge
            )

            if (log.sleepDebt > 0) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.history_debt, log.sleepDebt),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

private fun formatMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    return formatter.format(Date(millis))
}