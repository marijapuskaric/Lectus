package com.example.lectus.composables

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextButton(
    value: String,
    fontFamily: androidx.compose.ui.text.font.FontFamily,
    onContinueClicked: () -> Unit
)
{
    TextButton(onClick = onContinueClicked) {
        Text(
            fontFamily = fontFamily,
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.tertiary,
            text = value,
            modifier = Modifier
                .padding(top = 25.dp)
                .requiredWidth(IntrinsicSize.Max),
        )
    }
}