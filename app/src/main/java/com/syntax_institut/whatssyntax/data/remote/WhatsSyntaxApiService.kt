package com.syntax_institut.whatssyntax.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.syntax_institut.whatssyntax.data.datamodels.Calls
import com.syntax_institut.whatssyntax.data.datamodels.Chat
import com.syntax_institut.whatssyntax.data.datamodels.Contact
import com.syntax_institut.whatssyntax.data.datamodels.Messages
import com.syntax_institut.whatssyntax.data.datamodels.Profile
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


// Base URL for the API
const val BASE_URL = "http://81.169.201.230:8080"

// Logger instance for debugging API responses
private val logger: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}


private val httpClient: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()


// Moshi instance for JSON parsing
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


// Retrofit instance for making network requests
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(httpClient)
    .build()


// Retrofit API service interface
interface WhatsSyntaxApiService {

    // Endpoint for fetching chats
    @GET("/group/{number}/chats")
    suspend fun getChats(
        @Path("number") number: Int,
        @Query("key") key: String
    ): List<Chat>


    @GET("/group/{number}/chat/{chatId}")
    suspend fun getMessages(
        @Path("number") number: Int,
        @Path("chatId") chatId: Int,
        @Query("key") key: String
    ): List<Messages>


    @POST("/group/{number}/chats/{chatId}/new-message")
    suspend fun sendMessage(
        @Path("number") number: Int,
        @Path("chatId") chatId: Int,
        @Body messages: Messages,
        @Query("key") key: String,
    )


    // Endpoint for fetching contacts
    @GET("/group/{number}/contacts")
    suspend fun getContacts(
        @Path("number") number: Int,
        @Query("key") key: String
    ): List<Contact>

    // Endpoint for fetching calls
    @GET("/group/{number}/calls")
    suspend fun getCalls(
        @Path("number") number: Int,
        @Query("key") key: String
    ): List<Calls>


    // Endpoint for fetching profile
    @GET("/group/{number}/profile")
    suspend fun getProfile(
        @Path("number") number: Int,
        @Query("key") key: String
    ): Profile


    // Endpoint for updating profile
    @POST("/group/{number}/profile")
    suspend fun setProfile(
        @Path("number") number: Int,
        @Body profile: Profile, @Query("key") key: String
    )
}


// Singleton object for accessing Retrofit API service
object WhatsSyntaxApi {
    val retrofitService: WhatsSyntaxApiService by lazy { retrofit.create(WhatsSyntaxApiService::class.java) }
}