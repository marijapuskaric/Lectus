package com.example.lectus.screens

import android.annotation.SuppressLint
import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lectus.R
import com.example.lectus.composables.CustomOutlinedTextField
import com.example.lectus.composables.CustomTextButton
import com.example.lectus.composables.Header
import com.example.lectus.getFontFamily
import com.example.lectus.viewmodels.LoginViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lectus.authentication.Login
import com.example.lectus.authentication.Utils.Companion.showMessage

@SuppressLint("UnrememberedMutableState")
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavRegisterPage: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var validateEmail by rememberSaveable { mutableStateOf(true) }
    var validatePassword by rememberSaveable { mutableStateOf(true) }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    val validateEmailError = stringResource(id = R.string.vaildate_email_e)
    val validatePasswordError = stringResource(id = R.string.vaildate_password_e)

    fun validateData(email: String, password: String): Boolean {
        val passwordRegex = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}".toRegex()

        validateEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        validatePassword = passwordRegex.matches(password)

        return validateEmail && validatePassword
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Header()
        Spacer(modifier = Modifier.height(70.dp))
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(10.dp)
        ) {
            Card(
                modifier = Modifier
                    .padding(32.dp)
                    .requiredHeight(IntrinsicSize.Max),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        fontFamily = getFontFamily(),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary,
                        text = stringResource(id = R.string.login),
                        modifier = Modifier
                            .padding(bottom = 35.dp, top = 35.dp),
                    )
                    Text(
                        fontFamily = getFontFamily(),
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.tertiary,
                        text = stringResource(id = R.string.email),
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .requiredWidth(IntrinsicSize.Max)
                            .align(Alignment.Start),
                        textAlign = TextAlign.Left
                    )
                    CustomOutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        showError = !validateEmail,
                        errorMessage = validateEmailError,
                        leadingIconImageVector = Icons.Default.AlternateEmail,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                    Text(
                        fontFamily = getFontFamily(),
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.tertiary,
                        text = stringResource(id = R.string.password),
                        modifier = Modifier
                            .padding(start = 20.dp, top = 25.dp)
                            .requiredWidth(IntrinsicSize.Max)
                            .align(Alignment.Start),
                        textAlign = TextAlign.Left,
                    )
                    CustomOutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        showError = !validatePassword,
                        errorMessage = validatePasswordError,
                        isPasswordField = true,
                        isPasswordVisible = isPasswordVisible,
                        onVisibilityChange = { isPasswordVisible = it },
                        leadingIconImageVector = Icons.Default.Password,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus(); validateData(email, password) }
                        )
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        onClick =
                        {
                            validateData(email, password)
                            viewModel.signInWithEmailAndPassword(
                                email,
                                password
                            )
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                    ) {
                        Text(
                            fontFamily = getFontFamily(),
                            text = stringResource(id = R.string.login),
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    CustomTextButton(
                        value = stringResource(id = R.string.register),
                        fontFamily = getFontFamily(),
                        onContinueClicked = onNavRegisterPage
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
        Login(
            showErrorMessage = { errorMessage ->
                showMessage(context, errorMessage)
            }
        )
    }
}
