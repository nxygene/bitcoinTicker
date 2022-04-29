package com.sberkozd.bitcointicker.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PublicInterestStats(
    @Json(name = "alexa_rank")
    var alexaRank: Int?,
    @Json(name = "bing_matches")
    var bingMatches: Any?
)