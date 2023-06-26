package com.example.lectus

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont

fun getFontFamily(): FontFamily {
    val fontProvider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
    val googleFont = GoogleFont("Playfair Display")
    return FontFamily(
        Font(googleFont = googleFont, fontProvider = fontProvider)
    )
}