package com.ptr.exchange.data.network.ipstack

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ptr.exchange.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object IpstackApi {
    const val BASE_URL = "http://api.ipstack.com/"
    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain
            .request()
            .url
            .newBuilder()
            .addQueryParameter("access_key", BuildConfig.ipstack_key)
            .build()

        val newRequest = chain
            .request()
            .newBuilder()
            .url(newUrl)
            .build()
        chain.proceed(newRequest)
    }
    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    private val ipstackClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(authInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .build()

    private fun makeService(): Retrofit {
        ipstackClient
        return Retrofit.Builder()
            .client(ipstackClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    fun fixerApi(): IpstackService = makeService().create(
        IpstackService::class.java)
}