package com.example.lectus.screens

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.lectus.composables.SetYearlyGoalDialog
import com.example.lectus.db.getYearlyGoal
import com.example.lectus.getFontFamily
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Calendar

@Composable
fun YearlyGoalScreen()
{
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val db = Firebase.firestore
    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    val context = LocalContext.current
    val goalLiveData = MutableLiveData<Long>()
    val finishedLiveData = MutableLiveData<Long>()
    val goal by goalLiveData.observeAsState()
    val finished by finishedLiveData.observeAsState()
    var goalBooks = 0
    var finishedBooks = 0
    val showSetYearlyGoalDialog = remember { mutableStateOf(false) }
    if (showSetYearlyGoalDialog.value) {
        SetYearlyGoalDialog(
            showDialog = showSetYearlyGoalDialog.value,
            context = context,
            onDismiss = { showSetYearlyGoalDialog.value = false })
    }
    if (currentUserUid != null)
    {
        getYearlyGoal(currentUserUid,db){ goal, finished ->

            goalLiveData.value = goal
            finishedLiveData.value = finished
            goalBooks = goal.toInt()
            finishedBooks = finished.toInt()
        }
    }
    if (goal != null)
    {
        goalBooks = goal!!.toInt()
    }
    if (finished != null)
    {
        finishedBooks = finished!!.toInt()
    }
    Box(contentAlignment = Alignment.TopCenter,
        modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(top = 10.dp)
            )
            {
                Text(
                    text = stringResource(id = R.string.year),
                    fontSize = 20.sp,
                    fontFamily = getFontFamily(),
                    color = MaterialTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = currentYear.toString(),
                    fontSize = 20.sp,
                    fontFamily = getFontFamily(),
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Row(horizontalArrangement = Arrangement.Center)
            {
                Text(
                    text = stringResource(id = R.string.your_yearly_goal),
                    fontSize = 18.sp,
                    fontFamily = getFontFamily(),
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = goalBooks.toString(),
                    fontSize = 18.sp,
                    fontFamily = getFontFamily(),
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            if (goal != null) {
                Box(modifier = Modifier.weight(1f))
                {
                    GoalGrid(goalBooks = goalBooks, finishedBooks = finishedBooks)
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
            if (finishedBooks >= goalBooks && goalBooks > 0)
            {
                Text(
                    text = stringResource(id = R.string.congratulations),
                    fontSize = 16.sp,
                    fontFamily = getFontFamily(),
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = stringResource(id = R.string.completed_yearly),
                    fontSize = 16.sp,
                    fontFamily = getFontFamily(),
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = { showSetYearlyGoalDialog.value = true },
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
fun GoalGrid (
    goalBooks: Int,
    finishedBooks: Int
){
    val gridColumns = 3
    val iconSize = 45.dp
    val bookStates = remember {
        val books = mutableListOf<Boolean>()
        for (i in 0 until goalBooks) {
            books.add(i < finishedBooks)
        }
        books
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(gridColumns),
        contentPadding = PaddingValues(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 15.dp),
        content = {
            items(bookStates.size) { index ->
                val iconVector: ImageVector = if (bookStates[index]) {
                    Icons.Default.Circle
                }
                else {
                    Icons.Default.RadioButtonUnchecked
                }
                Icon(
                    iconVector,
                    contentDescription = null,
                    modifier = Modifier.size(iconSize),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    )
}

