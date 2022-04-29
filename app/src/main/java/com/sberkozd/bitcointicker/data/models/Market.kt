package com.sberkozd.bitcointicker.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Market(
    @Json(name = "has_trading_incentive")
    var hasTradingIncentive: Boolean?,
    @Json(name = "identifier")
    var identifier: String?,
    @Json(name = "name")
    var name: String?
)