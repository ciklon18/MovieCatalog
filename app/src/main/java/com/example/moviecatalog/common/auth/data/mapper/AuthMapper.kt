package com.example.moviecatalog.common.auth.data.mapper

import com.example.moviecatalog.common.auth.domain.model.UserLoginModel
import com.example.moviecatalog.common.auth.domain.model.UserRegisterModel
import com.example.moviecatalog.common.ui.component.Gender
import com.example.moviecatalog.login.presentation.LoginUIState
import com.example.moviecatalog.registration.presentation.RegistrationUIState

fun RegistrationUIState.toUserRegisterModel() : UserRegisterModel {
    return UserRegisterModel(
        name = this.name,
        password = this.password,
        userName = this.login,
        email = this.email,
        birthDate = this.birthDate.toString(),
        gender = Gender.toInt(this.gender)
    )
}

fun LoginUIState.toUserLoginModel(): UserLoginModel{
    return UserLoginModel(
        username = this.login,
        password = this.password
    )
}