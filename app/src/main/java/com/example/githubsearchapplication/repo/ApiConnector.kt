package com.example.githubsearchapplication.repo

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConnector {

    private lateinit var retrofit: Retrofit
    private fun getRetrofit(): Retrofit {
        return if (this::retrofit.isInitialized) {
            retrofit

        } else {
            val interceptor =  HttpLoggingInterceptor()

            val client =  OkHttpClient.Builder().addInterceptor(interceptor).build()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .client(client)
                .baseUrl("https://api.github.com")
                .build()
            retrofit
        }
    }
    fun provideRepoApiService() :RepoService {
        return getRetrofit().create(RepoService::class.java)
    }

}