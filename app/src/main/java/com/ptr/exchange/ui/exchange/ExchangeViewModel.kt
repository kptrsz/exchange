package com.ptr.exchange.ui.exchange

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ptr.exchange.data.FixerRepository
import com.ptr.exchange.data.IpstackRepository
import com.ptr.exchange.data.network.fixer.FixerApi
import com.ptr.exchange.data.network.ipstack.IpstackApi
import com.ptr.exchange.model.fixer.LatestResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ExchangeViewModel : ViewModel() {
    private val job = Job()
    private val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)
    private val fixerRepository: FixerRepository = FixerRepository(FixerApi.fixerApi())
    private val ipstackRepository: IpstackRepository = IpstackRepository(IpstackApi.fixerApi())

    val localCurrency = MutableLiveData<String>()
    val value = MutableLiveData<String>()

    fun getLocalCurrency(ip: String) {
        scope.launch {
            val code = ipstackRepository.getLocalCurrency(ip)?.value?.currency?.code
            localCurrency.postValue(code?:"HUF")
        }
    }

    fun convert(from: String, to: String, amount: Int) {
        scope.launch {
            value.postValue(fixerRepository.convert(from, to, amount).value?.result?.toString())
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()
}
