package com.example.utstam.api

import com.example.utstam.model.Report
import com.example.utstam.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("users.json")
    fun getUsers(): Call<List<User>>

    @GET("reports")
    fun getReports(): Call<List<Report>>

    @POST("reports")
    fun createReport(@Body report: Report): Call<Report>
}