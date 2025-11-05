package com.app.matchup.utilities

import java.security.MessageDigest

object PasswordEncryption {

    fun hashPassword(password: String): String{
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)

        // Convert to Hexadecimal
        return digest.joinToString("") { "%02x".format(it) }
    }
}