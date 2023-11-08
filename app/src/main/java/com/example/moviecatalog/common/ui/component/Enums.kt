package com.example.moviecatalog.common.ui.component

enum class Gender{
    Male,
    Female;

    companion object {
        fun fromInt(gender: Int): Gender {
            return when(gender){
                0 -> Male
                else -> Female
            }
        }
        fun toInt(gender: Gender): Int {
            return when(gender){
                Male -> 0
                else -> 1
            }
        }
    }
}

enum class FieldType {
    Name,
    Gender,
    Login,
    Email,
    BirthDate,
    Password,
    RepeatedPassword,
    Link
}

enum class MyTab {
    Main,
    Favorite,
    Profile
}