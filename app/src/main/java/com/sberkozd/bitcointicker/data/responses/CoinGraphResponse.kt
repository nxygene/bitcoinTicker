package com.sberkozd.bitcointicker.data.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoinGraphResponse (
    @Json(name = "prices")
    val coinChange : List<DoubleArray>
)