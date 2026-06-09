package com.example.utstam.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String? = null,
    var name: String? = null,
    val email: String,
    var password: String,
    val foto: String? = null
) : Parcelable