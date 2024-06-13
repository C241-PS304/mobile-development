package com.bangkit2024.facetrack.data.remote.retrofit

import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    // Login User
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    )

    // Register User
    @FormUrlEncoded
    @POST("user/signup")
    suspend fun register(
        @Field("email") email: String,
        @Field("password") password: String
    )

    // Update data user
    @FormUrlEncoded
    @PUT("user/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Field("nama") nama: String,
        @Field("no_telp") noTelp: String
    )

    // Get All Program
    @GET("program")
    suspend fun getPrograms(
        @Header("Authorization") token: String,
    )

    // Add new program
    @FormUrlEncoded
    @POST("program/add")
    suspend fun addProgram(
        @Header("Authorization") token: String,
        @Field("namaProgram") namaProgram: String,
        @Field("skincares") skincares: String, // Array to String
    )

    // Get Detail Program
    @GET("program/{id}")
    suspend fun getDetailProgram(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    )

    // Update Program
    @FormUrlEncoded
    @PUT("program/{id}")
    suspend fun updateStatusProgram(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Field("isActive") isActive: String,
    )

    // Add Scan
    @Multipart
    @POST("scan/add")
    suspend fun addScan(
        @Header("Authorization") token: String,
        @Part gambar: MultipartBody.Part,
    )

}