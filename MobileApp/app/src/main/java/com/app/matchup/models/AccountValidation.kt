package com.app.matchup.models

data class AccountValidation(
    val nameError: String? = null,
    val emailError: String? = null,
    val countryError: String? = null,
    val cityError: String? = null,
    val mobilePhoneError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val passwordMatchError: String? = null,
    val genderError: String? = null,
    val favSportError: String? = null
)
