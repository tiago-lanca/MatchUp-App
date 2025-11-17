package com.app.matchup.services

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.app.matchup.models.EventFilter
import com.app.matchup.models.Sport
import com.app.matchup.models.User
import com.google.gson.Gson
import java.util.Date
import java.util.UUID

object UserSession {

    private const val PREF_NAME = "user_session"
    private const val KEY_IS_LOGGED_IN = "isLoggedIn"
    private const val KEY_USER = "logged_user"
    private const val KEY_USER_ID = "userId"
    private const val KEY_EMAIL = "userEmail"

    private const val FILTER_GENDER = "filter_gender"
    private const val FILTER_SPORT = "filter_sport"
    private const val FILTER_CITY = "filter_city"
    private const val FILTER_ONLY_MY_EVENTS = "filter_my_events"
    private const val FILTER_START_DATE = "filter_start_date"
    private const val FILTER_END_DATE = "filter_end_date"


    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserSession(context: Context, user: User){
        getPrefs(context).edit {
            putBoolean(KEY_IS_LOGGED_IN, true)
                .putString(KEY_EMAIL, user.email)
                .putString(KEY_USER_ID, user.id.toString())
        }
    }

    suspend fun getUser(context: Context): User? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val userJson = prefs.getString(KEY_USER_ID, null)
        return if (userJson != null) UserService.GetUserById(UUID.fromString(userJson))
            else null
    }

    fun isLoggedIn(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getUserEmail(context: Context): String? {
        return getPrefs(context).getString(KEY_EMAIL, null)
    }

    fun getUserId(context: Context): String? {
        return getPrefs(context).getString(KEY_USER_ID, null)
    }

    fun logoutUser(context: Context) {
        getPrefs(context).edit { clear() }
    }
}