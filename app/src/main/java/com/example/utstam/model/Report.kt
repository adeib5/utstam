package com.example.utstam.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Report(
    val id: String,
    val userId: String,
    val reporterDisplayName: String,
    val category: String,
    val location: String,
    val date: String,
    val description: String,
    val photoUri: String?,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "Diproses",
    val adminResponse: String? = null
) : Parcelable