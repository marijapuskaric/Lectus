package com.example.lectus.screens

import android.annotation.SuppressLint
import android.util.Log
import android.util.Patterns
import android.widget.Toast
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
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lectus.MainActivity
import com.example.lectus.R
import com.example.lectus.authentication.Register
import com.example.lectus.composables.CustomOutlinedTextField
import com.example.lectus.composables.CustomTextButton
import com.example.lectus.composables.Header
import com.example.lectus.getFontFamily
import com.example.lectus.viewmodels.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth


@SuppressLint("UnrememberedMutableState")
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavLoginPage: () -> Unit,
    modifier: Modifier = Modifier
){
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    var username by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    var validateEmail by rememberSaveable { mutableStateOf(true) }
    var validatePassword by rememberSaveable { mutableStateOf(true) }
    var validateConfirmPassword by rememberSaveable { mutableStateOf(true) }
    var validatePasswordsEqual by rememberSaveable { mutableStateOf(true) }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var isConfirmPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var validateUsername by rememberSaveable { mutableStateOf(false) }

    val validateEmailError = "The format of the email doesn't seem right"
    val validatePasswordError = "Password must contain capital and non-capital letters, a number and at least 8 characters"
    val validateEqualPasswordError = "Passwords must be equal"

    fun validateData(email: String, password: String, confirmPassword: String): Boolean {
        val passwordRegex = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}".toRegex()


        validateEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        validatePassword = passwordRegex.matches(password)
        validateConfirmPassword = passwordRegex.matches(confirmPassword)
        validatePasswordsEqual = password == confirmPassword
        validateUsername = username.isNotBlank()

        return validateEmail && validatePassword && validateConfirmPassword && validatePasswordsEqual && validateUsername
    }
    fun validate (
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean{
        if (validateData(email, password, confirmPassword)){
            Log.d(MainActivity::class.java.simpleName, "Username: $username, email: $email, password: $password, confirm password: $confirmPassword")
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(context, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }else{
            Toast.makeText(context, "Please, review fields", Toast.LENGTH_SHORT).show()
        }
        return validateEmail && validatePassword && validateConfirmPassword && validatePasswordsEqual && validateUsername
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.champagne))
    ) {
        Header()
        Spacer(modifier = Modifier.height(70.dp))
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(10.dp)) {
            Card(
                modifier = Modifier
                    .padding(32.dp)
                    .requiredHeight(IntrinsicSize.Max),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.tan)
                )
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
                        color = colorResource(id = R.color.caput_mortuum),
                        text = stringResource(id = R.string.register),
                        modifier = Modifier
                            .padding(bottom = 35.dp, top = 35.dp),
                    )
                    Text(
                        fontFamily = getFontFamily(),
                        fontSize = 10.sp,
                        color = colorResource(id = R.color.caput_mortuum),
                        text = stringResource(id = R.string.username),
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .requiredWidth(IntrinsicSize.Max)
                            .align(Alignment.Start),
                        textAlign = TextAlign.Left
                    )
                    CustomOutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = stringResource(id = R.string.username),
                        leadingIconImageVector = Icons.Default.PermIdentity,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )

                    Text(
                        fontFamily = getFontFamily(),
                        fontSize = 10.sp,
                        color = colorResource(id = R.color.caput_mortuum),
                        text = stringResource(id = R.string.email),
                        modifier = Modifier
                            .padding(start = 20.dp, top = 25.dp)
                            .requiredWidth(IntrinsicSize.Max)
                            .align(Alignment.Start),
                        textAlign = TextAlign.Left
                    )
                    CustomOutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = stringResource(id = R.string.email),
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
                        color = colorResource(id = R.color.caput_mortuum),
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
                        label = stringResource(id = R.string.password),
                        showError = !validatePassword,
                        errorMessage = validatePasswordError,
                        isPasswordField = true,
                        isPasswordVisible = isPasswordVisible,
                        onVisibilityChange = { isPasswordVisible = it },
                        leadingIconImageVector = Icons.Default.Password,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                    Text(
                        fontFamily = getFontFamily(),
                        fontSize = 10.sp,
                        color = colorResource(id = R.color.caput_mortuum),
                        text = stringResource(id = R.string.confirm_password),
                        modifier = Modifier
                            .padding(start = 20.dp, top = 25.dp)
                            .requiredWidth(IntrinsicSize.Max)
                            .align(Alignment.Start),
                        textAlign = TextAlign.Left,
                    )
                    CustomOutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = stringResource(id = R.string.confirm_password),
                        showError = !validateConfirmPassword || !validatePasswordsEqual,
                        errorMessage = if (!validateConfirmPassword) validatePasswordError else validateEqualPasswordError,
                        isPasswordField = true,
                        isPasswordVisible = isConfirmPasswordVisible,
                        onVisibilityChange = { isConfirmPasswordVisible = it },
                        leadingIconImageVector = Icons.Default.Password,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        )
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        onClick = {
                            if (validate(username, email, password, confirmPassword))
                            {
                                viewModel.signUpWithEmailAndPassword(email, password)
                            }

                        },
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.redwood)),

                    ) {
                        Text(
                            fontFamily = getFontFamily(),
                            text = stringResource(id = R.string.register),
                            color = colorResource(id = R.color.champagne)
                        )

                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    CustomTextButton(
                        value = stringResource(id = R.string.login),
                        fontFamily = getFontFamily(),
                        onNavLoginPage
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
        Register()
    }
}