package com.app.matchup.extensions

import com.app.matchup.R
import com.app.matchup.models.Sport

fun Sport.getSportIcon(): Int {
    return when(this.name.lowercase()){
        "football" -> R.drawable.football_icon
        "futsal" -> R.drawable.futsalball_icon
        "running" -> R.drawable.running_icon
        "paddle" -> R.drawable.padel_icon
        "basketball" -> R.drawable.basketball_icon
        else -> 0
    }
}