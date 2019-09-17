package com.ptr.exchange.ui.history

import androidx.lifecycle.ViewModel
import com.ptr.exchange.data.FixerRepository
import com.ptr.exchange.data.network.fixer.FixerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.coroutines.CoroutineContext


class HistoryViewModel : ViewModel() {
    private val job = Job()
    private val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)
    private val fixerRepository: FixerRepository = FixerRepository(FixerApi.fixerApi())

    fun getHistory(base: String) {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val endDate = Calendar.getInstance()
        val startDate = endDate.add(Calendar.DATE, -7)
        scope.launch {
            val code = fixerRepository.getHistory(base, formatter.format(startDate), formatter.format(endDate))
        }
    }
}