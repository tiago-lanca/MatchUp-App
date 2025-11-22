package com.app.matchup.extensions

import com.app.matchup.models.Event
import java.util.Date

fun Event?.isNull() = this == null

fun List<Event>.removePastEvents(): List<Event> {
    return this.filter { it.date!! >= Date() }
}

fun List<Event>.sortByDate(): List<Event> {
    return this.sortedBy { it.date }
}

