package com.example.utstam.api

import com.example.utstam.model.Report
import com.example.utstam.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("reports")
    fun getReports(): Call<List<Report>>

    @POST("reports")
    fun createReport(@Body report: Report): Call<Report>

    @POST("login")
    fun login(@Body user: User): Call<User>
}