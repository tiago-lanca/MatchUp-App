package com.app.matchup.extensions

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.matchup.R

fun Int.getSportIconSize(): Dp {
    return when(this) {
        R.drawable.football_icon -> 25.dp
        R.drawable.padel_icon -> 35.dp
        R.drawable.running_icon -> 35.dp
        R.drawable.futsalball_icon -> 25.dp
        R.drawable.basketball_icon -> 25.dp
        else -> 25.dp
    }
}

fun Int.getSportPaddingEnd(): Dp {
    return when(this) {
        R.drawable.football_icon -> 15.dp
        R.drawable.padel_icon -> 5.dp
        R.drawable.running_icon -> 5.dp
        R.drawable.futsalball_icon -> 15.dp
        R.drawable.basketball_icon -> 15.dp
        else -> 15.dp
    }
}