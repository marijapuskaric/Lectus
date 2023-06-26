package com.example.lectus.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lectus.composables.Header
import com.example.lectus.viewmodels.SettingsViewModel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.example.lectus.R
import com.example.lectus.composables.CustomBottomNavigation
import com.example.lectus.composables.ResetPasswordDialog
import com.example.lectus.data.TAG
import com.example.lectus.db.getUser
import com.example.lectus.getFontFamily
import com.example.lectus.viewmodels.ThemeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsScreen(
    mainNavController: NavHostController,
    themeViewModel: ThemeViewModel,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val db = Firebase.firestore
    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    val usernameLiveData = MutableLiveData<String>()
    val emailLiveData = MutableLiveData<String>()
    val username by usernameLiveData.observeAsState()
    val email by emailLiveData.observeAsState()
    val showResetPasswordDialog = remember { mutableStateOf(false) }
    if (currentUserUid != null)
    {
        getUser(currentUserUid, db){ username, email ->
            if (username != null && email != null)
            {
                usernameLiveData.value = username
                emailLiveData.value = email
            }
            else {
                Log.e(TAG, "Failed to retrieve user data")
            }
        }
    }
    if (showResetPasswordDialog.value ) {
        ResetPasswordDialog(
            email = email ?: "",
            viewModel = viewModel,
            showDialog = showResetPasswordDialog.value,
            onDismiss = { showResetPasswordDialog.value = false })
    }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        )
        {
            Header()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 48.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.settings),
                        fontSize = 24.sp,
                        fontFamily = getFontFamily(),
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    if (username != null && email != null) {
                        Text(
                            text = username.toString(),
                            fontSize = 35.sp,
                            fontFamily = getFontFamily(),
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        Row(horizontalArrangement = Arrangement.Center)
                        {
                            Text(
                                text = stringResource(id = R.string.email_display),
                                fontSize = 18.sp,
                                fontFamily = getFontFamily(),
                                color = MaterialTheme.colorScheme.tertiary
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            Text(
                                text = email.toString(),
                                fontSize = 18.sp,
                                fontFamily = getFontFamily(),
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
                    Button(
                        onClick = {
                            showResetPasswordDialog.value = true
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                    ) {
                        Text(
                            text = stringResource(id = R.string.change_password),
                            fontFamily = getFontFamily(),
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
                    Text(
                        text = stringResource(id = R.string.change_theme),
                        fontSize = 16.sp,
                        fontFamily = getFontFamily(),
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                themeViewModel.setDarkTheme(false)
                            },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),

                            ) {
                            Text(
                                text = stringResource(id = R.string.light_theme),
                                fontFamily = getFontFamily(),
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                        Spacer(modifier = Modifier.width(15.dp))
                        Button(
                            onClick = {
                                themeViewModel.setDarkTheme(true)
                            },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                        ) {
                            Text(
                                text = stringResource(id = R.string.dark_theme),
                                fontFamily = getFontFamily(),
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
                    Button(
                        onClick = { viewModel.signOut() },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                    ) {
                        Text(
                            text = stringResource(id = R.string.log_out),
                            fontFamily = getFontFamily(),
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                }
            }

        }
    CustomBottomNavigation(mainNavController = mainNavController)

}