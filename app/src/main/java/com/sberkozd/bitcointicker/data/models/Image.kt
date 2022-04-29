package com.sberkozd.bitcointicker.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    @Json(name = "large")
    var large: String?,
    @Json(name = "small")
    var small: String?,
    @Json(name = "thumb")
    var thumb: String?
)