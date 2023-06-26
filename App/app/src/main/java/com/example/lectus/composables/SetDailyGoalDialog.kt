package com.example.lectus.composables

import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.runtime.remember
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.lectus.R
import com.example.lectus.db.setUserDailyGoal
import com.example.lectus.getFontFamily
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun SetDailyGoalDialog(
    showDialog: Boolean,
    context: Context,
    onDismiss: () -> Unit
)
{

    val db = Firebase.firestore
    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    var dailyGoal by rememberSaveable { mutableStateOf("") }
    var selectedGoal by remember { mutableStateOf("") }
    val goalOptions = listOf(
        stringResource(id = R.string.pages),
        stringResource(id = R.string.chapters),
        )
    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() },
        ) {
            val focusManager = LocalFocusManager.current
            Surface(
                modifier = Modifier
                    .padding(2.dp)
                    .width(500.dp),
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.primary
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)

                ) {
                    Row(modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Top

                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                                .clickable {
                                    onDismiss()
                                }
                        ) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.width(40.dp)
                            )
                        }
                    }
                    Text(
                        text = stringResource(id = R.string.set_daily_goal),
                        fontFamily = getFontFamily(),
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(bottom = 20.dp, top = 5.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = stringResource(id = R.string.choose_goal),
                        fontFamily = getFontFamily(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .padding(bottom = 35.dp, top = 35.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    goalOptions.forEach { goal ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedGoal = goal }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedGoal == goal,
                                onClick = { selectedGoal = goal },
                                modifier = Modifier
                                    .padding(end = 8.dp),
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.tertiary,
                                    unselectedColor = MaterialTheme.colorScheme.tertiary
                                )
                            )
                            Text(text = goal,
                                fontFamily = getFontFamily(),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.tertiary,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.select_number),
                        fontFamily = getFontFamily(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .padding(bottom = 35.dp, top = 35.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomOutlinedTextField(
                        value = dailyGoal,
                        onValueChange = {dailyGoal = it},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {focusManager.clearFocus()}
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                if (selectedGoal.isNotEmpty() && dailyGoal.isNotEmpty() && currentUserUid != null) {
                                    setUserDailyGoal(selectedGoal, dailyGoal.toInt(), currentUserUid, db, context)
                                }
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),

                            ) {
                            Text(text = stringResource(id = R.string.confirmation),
                                fontFamily = getFontFamily(),
                                color = MaterialTheme.colorScheme.background,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}