package com.example.lectus.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lectus.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    leadingIconImageVector: ImageVector? = null,
    leadingIconDescription: String = "",
    isPasswordField: Boolean = false,
    isPasswordVisible: Boolean = false,
    onVisibilityChange: (Boolean) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    showError: Boolean = false,
    errorMessage: String = ""
){
    OutlinedTextField(
        value = value,
        onValueChange = {onValueChange(it)},
        modifier = Modifier
            .requiredWidth(325.dp)
            .padding(start = 15.dp, end = 15.dp)
            .background(colorResource(id = R.color.champagne), shape = RoundedCornerShape(10.dp)),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = colorResource(id = R.color.champagne),
            unfocusedBorderColor = colorResource(id = R.color.caput_mortuum),
            focusedBorderColor = colorResource(id = R.color.caput_mortuum)),
        singleLine = false,
        shape = RoundedCornerShape(10.dp),
        textStyle = TextStyle(fontSize = 12.sp),
        leadingIcon = leadingIconImageVector?.let { vector ->
            {
                Icon(
                    imageVector = vector,
                    contentDescription = leadingIconDescription,
                    tint = if (showError) MaterialTheme.colorScheme.error else colorResource(id = R.color.caput_mortuum)
                )
            }
        },
        isError = showError,
        trailingIcon = {
            if (showError && !isPasswordField) Icon(imageVector = Icons.Filled.Error, contentDescription = "show error icon" )
            if(isPasswordField){
                IconButton(onClick = { onVisibilityChange(!isPasswordVisible) }) {
                    Icon(
                        imageVector = if(isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle password visibility",
                    tint = colorResource(id = R.color.caput_mortuum)
                    )
                }
            }
        },
        visualTransformation = when {
            isPasswordField && isPasswordVisible -> VisualTransformation.None
            isPasswordField -> PasswordVisualTransformation()
            else -> VisualTransformation.None
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
    if (showError) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(start = 20.dp)
                .width(300.dp),
            textAlign = TextAlign.Left
        )
    }

}