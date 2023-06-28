package com.example.lectus.composables.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewModelScope
import com.example.lectus.R
import com.example.lectus.getFontFamily
import com.example.lectus.viewmodels.SettingsViewModel
import kotlinx.coroutines.launch

@Composable
fun ResetPasswordDialog(
    email: String,
    viewModel: SettingsViewModel,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog)
    {
        Dialog(
            onDismissRequest = { onDismiss() },
        ) {
            Surface(
                modifier = Modifier
                    .padding(30.dp),
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.primary
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = stringResource(id = R.string.change_password_instruction),
                        fontFamily = getFontFamily(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .padding(bottom = 35.dp, top = 35.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick =
                            {
                                viewModel.viewModelScope.launch {
                                    viewModel.resetPassword(email)
                                }
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                        ) {
                            Text(
                                text = stringResource(id = R.string.confirmation),
                                fontFamily = getFontFamily(),
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                        Spacer(modifier = Modifier.width(15.dp))
                        Button(
                            onClick = { onDismiss() },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                        ) {
                            Text(text = stringResource(id = R.string.cancel),
                                fontFamily = getFontFamily(),
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                }
            }
        }
    }
}