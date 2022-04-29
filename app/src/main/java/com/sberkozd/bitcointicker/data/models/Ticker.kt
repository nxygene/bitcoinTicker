package com.sberkozd.bitcointicker.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ticker(
    @Json(name = "base")
    var base: String?,
    @Json(name = "bid_ask_spread_percentage")
    var bidAskSpreadPercentage: Double?,
    @Json(name = "coin_id")
    var coinId: String?,
    @Json(name = "converted_last")
    var convertedLast: ConvertedLast?,
    @Json(name = "converted_volume")
    var convertedVolume: ConvertedVolume?,
    @Json(name = "is_anomaly")
    var isAnomaly: Boolean?,
    @Json(name = "is_stale")
    var isStale: Boolean?,
    @Json(name = "last")
    var last: Double?,
    @Json(name = "last_fetch_at")
    var lastFetchAt: String?,
    @Json(name = "last_traded_at")
    var lastTradedAt: String?,
    @Json(name = "market")
    var market: Market?,
    @Json(name = "target")
    var target: String?,
    @Json(name = "target_coin_id")
    var targetCoinId: String?,
    @Json(name = "timestamp")
    var timestamp: String?,
    @Json(name = "token_info_url")
    var tokenInfoUrl: Any?,
    @Json(name = "trade_url")
    var tradeUrl: String?,
    @Json(name = "trust_score")
    var trustScore: String?,
    @Json(name = "volume")
    var volume: Double?
)