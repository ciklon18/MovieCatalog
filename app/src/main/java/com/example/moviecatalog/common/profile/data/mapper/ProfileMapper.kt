package com.example.moviecatalog.common.profile.data.mapper

import com.example.moviecatalog.common.profile.domain.model.Profile
import com.example.moviecatalog.common.ui.component.Gender
import com.example.moviecatalog.common.util.convertDateStringToLocalDate
import com.example.moviecatalog.common.util.toDateString
import com.example.moviecatalog.profile.presentation.ProfileUIState

fun Profile.toProfileUIState(): ProfileUIState {
    val localDate = convertDateStringToLocalDate(this.birthDate)
    return ProfileUIState(
        id = this.id,
        nickName = this.nickName,
        email = this.email,
        avatarLink = this.avatarLink ?: "",
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

