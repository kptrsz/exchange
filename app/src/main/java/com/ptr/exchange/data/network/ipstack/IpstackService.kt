package com.ptr.exchange.data.network.ipstack

import com.ptr.exchange.model.ipstack.IpResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IpstackService {
    @GET("/{ip}")
    fun getLocalCurrency(@Path("ip") ip: String): Deferred<Response<IpResponse>>
}