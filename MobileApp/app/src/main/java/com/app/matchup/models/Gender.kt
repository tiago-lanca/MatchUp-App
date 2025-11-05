package com.app.matchup.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.app.matchup.R

data class Gender (
    val name: String,
    val iconVector: ImageVector? = null,
    val iconDraw: Int? = null
) {
    companion object {
        val Male = Gender("M", Icons.Default.Male)
        val Female = Gender("F", Icons.Default.Female)
        val Mix = Gender("Mix", iconDraw = R.drawable.mix_gender)

        val allGenders = listOf(Male, Female, Mix)

    }
}