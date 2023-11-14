package com.example.moviecatalog.common.profile.data.mapper

import com.example.moviecatalog.common.profile.domain.model.Profile
import com.example.moviecatalog.common.ui.component.Gender
import com.example.moviecatalog.common.util.convertDateStringToLocalDate
import com.example.moviecatalog.common.util.toDateString
import com.example.moviecatalog.profile.presentation.ProfileUIState
import com.example.moviecatalog.registration.presentation.RegistrationUIState
import java.util.UUID

fun Profile.toProfileUIState(): ProfileUIState {
    val localDate = convertDateStringToLocalDate(this.birthDate)
    return ProfileUIState(
        id = this.id,
        nickName = this.nickName,
        email = this.email,
        avatarLink = this.avatarLink,
        name = this.name,
        birthDate = localDate,
        gender = Gender.fromInt(this.gender)
    )
}

fun ProfileUIState.toProfile(): Profile {
    return Profile(
        id = this.id,
        nickName = this.nickName,
        email = this.email,
        avatarLink = this.avatarLink,
        name = this.name,
        birthDate = this.birthDate?.toDateString() ?: "",
        gender = Gender.toInt(this.gender)
    )
}

fun RegistrationUIState.toProfile(): Profile {
    val birthDate = this.birthDate?.toDateString() ?: ""
    return Profile(
        id = UUID.randomUUID().toString(),
        nickName = this.name,
        email = this.email,
        name = this.name,
        avatarLink = "",
        birthDate = birthDate,
        gender = Gender.toInt(this.gender)
    )
}
