package com.sberkozd.bitcointicker.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CodeAdditionsDeletions4Weeks(
    @Json(name = "additions")
    var additions: Int?,
    @Json(name = "deletions")
    var deletions: Int?
)