package com.example.utstam.model

import java.io.Serializable

data class Article(
    val id: Int,
    val title: String,
    val content: String,
    val imageResId: Int
) : Serializable