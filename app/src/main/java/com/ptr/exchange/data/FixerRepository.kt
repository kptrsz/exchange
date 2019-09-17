package com.ptr.exchange.data

import androidx.lifecycle.MutableLiveData
import com.ptr.exchange.data.network.fixer.FixerService
import com.ptr.exchange.model.fixer.ConvertResponse
import com.ptr.exchange.model.fixer.HistoryResponse
import com.ptr.exchange.model.fixer.LatestResponse
import com.ptr.exchange.utils.BaseRepository

class FixerRepository(private val service: FixerService) : BaseRepository() {

    suspend fun getLatestRates(): MutableLiveData<LatestResponse>? {
        val response = safeApiCall(
            { service.getCurrencies().await() },
            "Fetch error"
        )

        val mutableLiveData = MutableLiveData<LatestResponse>()
        mutableLiveData.postValue(response)
        return mutableLiveData
    }

    suspend fun convert(from: String, to: String, amount: Int): MutableLiveData<ConvertResponse> {
        val response = safeApiCall(
            { service.convert(from, to, amount).await() },
            "Fetch error"
        )

        val mutableLiveData = MutableLiveData<ConvertResponse>()
        mutableLiveData.postValue(response)
        return mutableLiveData
    }

    suspend fun getHistory(base: String, start: String?, end: String?): Any {
        val response = safeApiCall(
            { service.history(base, start, end).await() },
            "Fetch error"
        )

        val mutableLiveData = MutableLiveData<HistoryResponse>()
        mutableLiveData.postValue(response)
        return mutableLiveData
    }
}