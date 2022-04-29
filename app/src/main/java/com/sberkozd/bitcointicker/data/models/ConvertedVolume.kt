package com.sberkozd.bitcointicker.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConvertedVolume(
    @Json(name = "btc")
    var btc: Double?,
    @Json(name = "eth")
    var eth: Double?,
    @Json(name = "usd")
    var usd: Long?
)