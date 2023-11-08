package com.example.moviecatalog.common.profile.data.mapper

import com.example.moviecatalog.common.profile.domain.model.Profile
import com.example.moviecatalog.common.ui.component.Gender
import com.example.moviecatalog.profile.presentation.ProfileUIState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
fun convertDateStringToLocalDate(dateString: String): LocalDate? {
    val formatterWithMillis = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS[XXX][X]")
    return try {
        val parsedDateTime = LocalDateTime.parse(dateString, formatterWithMillis)
        parsedDateTime.toLocalDate()
    } catch (e: Exception) {
        val formatterWithoutMillis = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        try {
            val parsedDateTime = LocalDateTime.parse(dateString, formatterWithoutMillis)
            parsedDateTime.toLocalDate()
        } catch (e: Exception) {
            null
        }
    }
}

fun LocalDate.toDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    return this.atStartOfDay().format(formatter)
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