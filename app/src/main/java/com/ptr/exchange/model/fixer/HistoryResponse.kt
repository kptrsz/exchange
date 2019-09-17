package com.ptr.exchange.model.fixer

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HistoryResponse(
    val rates: List<Rates>
)