package com.mangoobyte.audiobookstest

import com.mangoobyte.audiobookstest.data.service.PodCastService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val API_VERSION = "v2"

@Module
@InstallIn(SingletonComponent::class)
object ApiCallClient {
    private const val BASE_URL = "https://listen-api-test.listennotes.com/api/"

    private fun client(): OkHttpClient.Builder =  OkHttpClient.Builder().apply {
        this.addInterceptor(
            HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BASIC
            }
        )
    }

    @Provides
    @Singleton
    fun retrofitClient(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun apiServiceCall(): PodCastService =
        retrofitClient().create(PodCastService::class.java)
}