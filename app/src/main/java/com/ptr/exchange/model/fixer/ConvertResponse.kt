package com.ptr.exchange.model.fixer

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConvertResponse(
    val result: Double?,
    val success: Boolean?
)