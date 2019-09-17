package com.ptr.exchange.model.fixer

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LatestResponse(
    val base: String?,
    val date: String?,
    val rates: Rates?
)