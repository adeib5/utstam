package com.example.utstam.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    var name: String,
    val email: String,
    var password: String
) : Parcelable