package com.app.matchup.utilities

import android.content.Context
import android.content.SharedPreferences
import com.app.matchup.models.User
import com.app.matchup.services.UserService
import com.google.gson.Gson
import java.util.UUID
import androidx.core.content.edit

object UserSession {

    private const val PREF_NAME = "user_session"
    private const val KEY_IS_LOGGED_IN = "isLoggedIn"
    private const val KEY_USER = "logged_user"
    private const val KEY_USER_ID = "userId"
    private const val KEY_EMAIL = "userEmail"

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