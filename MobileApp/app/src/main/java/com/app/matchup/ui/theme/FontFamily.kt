package com.app.matchup.ui.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.app.matchup.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val Poppins = FontFamily(
    Font(GoogleFont("Poppins"), provider, FontWeight.Normal),
    Font(GoogleFont("Poppins"), provider, FontWeight.Medium),
    Font(GoogleFont("Poppins"), provider, FontWeight.Bold)
)