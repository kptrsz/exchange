package com.ptr.exchange.data.network.fixer

import com.ptr.exchange.model.fixer.ConvertResponse
import com.ptr.exchange.model.fixer.HistoryResponse
import com.ptr.exchange.model.fixer.LatestResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FixerService {
    @GET("latest")
    fun getCurrencies(): Deferred<Response<LatestResponse>>

    @GET("convert")
    fun convert(@Query("from") from: String, @Query("to") to: String, @Query("amount") amount: Int): Deferred<Response<ConvertResponse>>

    @GET("timeseries")
    fun history(@Query("base") base: String, @Query("start_date") start: String?, @Query("end_date") end: String?): Deferred<Response<HistoryResponse>>

}