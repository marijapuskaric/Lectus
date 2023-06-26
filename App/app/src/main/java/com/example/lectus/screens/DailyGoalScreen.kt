package com.example.lectus.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.example.lectus.R
import com.example.lectus.composables.SetDailyGoalDialog
import com.example.lectus.data.LOG_DB
import com.example.lectus.db.addDailyGoalsAchieved
import com.example.lectus.db.getDailyGoal
import com.example.lectus.db.getMonthlyAchievements
import com.example.lectus.getFontFamily
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyGoalScreen()
{
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
    val monthName = getMonthNameFromCalendar(currentMonth)
    val db = Firebase.firestore
    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    val context = LocalContext.current
    val optionLiveData = MutableLiveData<String>()
    val numberLiveData = MutableLiveData<Long>()
    val option by optionLiveData.observeAsState()
    val number by numberLiveData.observeAsState()
    var chosenNumber = 0
    var chosenOption = option
    val showSetDailyGoalDialog = remember { mutableStateOf(false) }
    if (showSetDailyGoalDialog.value ) {
        SetDailyGoalDialog(
            showDialog = showSetDailyGoalDialog.value,
            context = context,
            onDismiss = { showSetDailyGoalDialog.value = false })
    }
    if (currentUserUid != null)
    {
        getDailyGoal(currentUserUid,db){ option, number ->
            if (option != null && number != null)
            {
                optionLiveData.value = option
                numberLiveData.value = number
            }
            else{
                Log.e(LOG_DB, "Failed to retrieve user goal data")
            }
        }
    }
    if (number != null)
    {
        chosenNumber = number!!.toInt()
    }
    if (number?.toInt() == 1)
    {
        if (option == "Pages") chosenOption = "Page"
        if (option == "Chapters") chosenOption = "Chapter"
    }
    Box(contentAlignment = Alignment.TopCenter,
        modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight()
        ) {
            Row(horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(top = 10.dp))
            {
                Text(
                    text = stringResource(id = R.string.your_daily_goal),
                    fontSize = 18.sp,
                    fontFamily = getFontFamily(),
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(5.dp))
                if (chosenNumber == 0 && chosenOption == null){
                    Text(
                        text = stringResource(id = R.string.not_set_daily_goal),
                        fontSize = 18.sp,
                        fontFamily = getFontFamily(),
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Bold
                    )
                }
                else {
                    Text(
                        text = chosenNumber.toString(),
                        fontSize = 18.sp,
                        fontFamily = getFontFamily(),
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = chosenOption.toString(),
                        fontSize = 18.sp,
                        fontFamily = getFontFamily(),
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.padding(20.dp))
            Text(
                text = stringResource(id = R.string.instructions),
                fontSize = 12.sp,
                fontFamily = getFontFamily(),
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.padding(15.dp))
            Text(
                text = monthName,
                fontSize = 18.sp,
                fontFamily = getFontFamily(),
                color = MaterialTheme.colorScheme.tertiary,
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Box(modifier = Modifier.weight(1f))
            {
                if (currentUserUid != null)
                {
                    MonthGrid(getDaysInCurrentMonth(), currentYear, monthName, currentUserUid, db)
                }
            }
            Button(
                onClick = { showSetDailyGoalDialog.value = true },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
            ) {
                Text(
                    text = stringResource(id = R.string.set_goal),
                    fontFamily = getFontFamily(),
                    color = MaterialTheme.colorScheme.background
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}

@Composable
fun MonthGrid(
    daysInMonth: Int,
    currentYear: Int,
    monthName: String,
    currentUserUid: String,
    db: FirebaseFirestore
) {
    var achieved by remember { mutableStateOf(false) }
    val gridColumns = 7
    val iconSize = 35.dp
    val dayStates = remember {
        mutableStateListOf<Boolean>().apply {
            for (i in 0 until daysInMonth) {
                add(false)
            }
        }
    }
    getMonthlyAchievements(currentUserUid,currentYear,monthName,daysInMonth,dayStates,db)

    LazyVerticalGrid(
        columns = GridCells.Fixed(gridColumns),
        contentPadding = PaddingValues(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 15.dp),
        content = {
            items(dayStates.size) { index ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val iconVector: ImageVector = if (dayStates[index]) {
                        Icons.Default.Circle
                    } else {
                        Icons.Default.RadioButtonUnchecked
                    }
                    Text(
                        text = (index + 1).toString(),
                        modifier = Modifier
                            .padding(end = 2.dp)
                            .width(12.dp),
                        fontFamily = getFontFamily(),
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.tertiary,
                        maxLines = 1
                    )
                    Icon(
                        iconVector,
                        contentDescription = null,
                        modifier = Modifier
                            .size(iconSize)
                            .clickable {
                                dayStates[index] =
                                    !dayStates[index]
                                achieved = !achieved
                                addDailyGoalsAchieved(
                                    currentYear,
                                    monthName,
                                    index + 1,
                                    achieved,
                                    currentUserUid,
                                    db
                                )
                            },
                        tint = MaterialTheme.colorScheme.secondary
                    )

                }
            }
        }
    )
}
@RequiresApi(Build.VERSION_CODES.O)
fun getDaysInCurrentMonth(): Int {
    val currentDate = LocalDate.now()
    val yearMonth = YearMonth.from(currentDate)
    return yearMonth.lengthOfMonth()
}

fun getMonthNameFromCalendar(currentMonth: Int): String {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.MONTH, currentMonth)

    val monthFormatter = SimpleDateFormat("MMMM", Locale.getDefault())
    return monthFormatter.format(calendar.time)
}