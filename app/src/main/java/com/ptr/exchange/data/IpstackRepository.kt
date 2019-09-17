package com.ptr.exchange.data

import androidx.lifecycle.MutableLiveData
import com.ptr.exchange.data.network.ipstack.IpstackService
import com.ptr.exchange.model.ipstack.IpResponse
import com.ptr.exchange.utils.BaseRepository

class IpstackRepository(private val service: IpstackService) : BaseRepository() {

    suspend fun getLocalCurrency(ip: String): MutableLiveData<IpResponse>? {
        val response = safeApiCall(
            { service.getLocalCurrency(ip).await() },
            "Fetch error"
        )

        val mutableLiveData = MutableLiveData<IpResponse>()
        mutableLiveData.postValue(response)
        return mutableLiveData
    }
}