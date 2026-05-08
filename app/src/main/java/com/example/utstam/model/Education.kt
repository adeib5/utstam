package com.example.utstam.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Education(
    val id: Int,
    val title: String,
    val description: String,
    val content: String,
    val imageResId: Int
) : Parcelable