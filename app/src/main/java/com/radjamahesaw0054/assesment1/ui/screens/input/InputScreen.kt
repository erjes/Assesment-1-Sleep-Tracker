package com.radjamahesaw0054.assesment1.ui.screens.input

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.radjamahesaw0054.assesment1.R
import com.radjamahesaw0054.assesment1.viewmodel.SleepViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(viewModel: SleepViewModel, onNavigateToHistory: () -> Unit) {
   var sleepInput by remember { mutableStateOf("") }
   var resultText by remember { mutableStateOf("") }
   val qualityOptions = listOf(stringResource(R.string.quality_poor), stringResource(R.string.quality_fair), stringResource(R.string.quality_good))
   var selectedQuality by remember { mutableStateOf(qualityOptions[1]) }
   val context = LocalContext.current

   Scaffold(
      topBar = {
         CenterAlignedTopAppBar(
            title = { Text(stringResource(R.string.app_name)) },
            actions = {
               IconButton(onClick = onNavigateToHistory) {
                  Icon(
                     painter = painterResource(id = R.drawable.history),
                     contentDescription = "History",
                     modifier = Modifier.size(24.dp)
                  )
               }
            }
         )
      }
   ) { padding ->
      Column(
         modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
         horizontalAlignment = Alignment.CenterHorizontally
      ) {
         Image(
            painter = painterResource(id = R.drawable.sleeping),
            contentDescription = null,
            modifier = Modifier
               .size(180.dp)
               .padding(bottom = 16.dp)
         )

         OutlinedTextField(
            value = sleepInput,
            onValueChange = { sleepInput = it },
            label = { Text(stringResource(R.string.input_label)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
         )

         Spacer(modifier = Modifier.height(16.dp))

         Text(stringResource(R.string.quality_label), style = MaterialTheme.typography.titleMedium)
         Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
         ) {
            qualityOptions.forEach { text ->
               Row(
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.fillMaxWidth()
               ) {
                  RadioButton(
                     selected = (text == selectedQuality),
                     onClick = { selectedQuality = text }
                  )
                  Text(
                     text = text,
                     style = MaterialTheme.typography.bodyLarge
                  )
               }
            }
         }

         Spacer(modifier = Modifier.height(24.dp))

         Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
               val duration = sleepInput.toDoubleOrNull()
               if (duration == null || duration > 24) {
                  Toast.makeText(context, context.getString(R.string.error_invalid), Toast.LENGTH_SHORT).show()
               } else {
                  viewModel.saveData(duration, selectedQuality)

                  val currentDebt = 8.0 - duration
                  val totalAccumulated = viewModel.getTotalDebt()

                  val todayDebtMsg = if (currentDebt > 0) {
                     context.getString(R.string.debt_today, currentDebt)
                  } else {
                     context.getString(R.string.message_enough_sleep)
                  }
                  val totalMsg = context.getString(R.string.message_total_debt, totalAccumulated)

                  resultText = "$todayDebtMsg\n$totalMsg"

                  sleepInput = ""
               }
            }
         ) {
            Text(stringResource(R.string.btn_calculate))
         }

         if (resultText.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))

            Card(
               modifier = Modifier.fillMaxWidth(),
               colors = CardDefaults.elevatedCardColors(
                  containerColor = MaterialTheme.colorScheme.primaryContainer
               )
            ) {
               Column(
                  modifier = Modifier.padding(16.dp),
                  horizontalAlignment = Alignment.Start
               ) {
                  Text(
                     text = stringResource(R.string.message_saved),
                     style = MaterialTheme.typography.labelLarge,
                     color = MaterialTheme.colorScheme.primary
                  )
                  Spacer(modifier = Modifier.height(8.dp))
                  Text(
                     text = resultText,
                     style = MaterialTheme.typography.bodyLarge,
                     color = MaterialTheme.colorScheme.onPrimaryContainer
                  )
                  Spacer(modifier = Modifier.height(16.dp))

                  OutlinedButton(
                     modifier = Modifier.fillMaxWidth(),
                     shape = RoundedCornerShape(12.dp),
                     onClick = {
                        val totalDebt = viewModel.getTotalDebt()
                        val sendMsg = context.getString(R.string.share_message, totalDebt)

                        val sendIntent: Intent = Intent().apply {
                           action = Intent.ACTION_SEND
                           putExtra(Intent.EXTRA_TEXT, sendMsg)
                           type = "text/plain"
                        }

                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                     }
                  ) {
                     Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                     Spacer(Modifier.width(8.dp))
                     Text(stringResource(R.string.btn_share))
                  }
               }
            }
         }
      }
   }
}