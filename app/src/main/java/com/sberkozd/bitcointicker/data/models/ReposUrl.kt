package com.sberkozd.bitcointicker.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReposUrl(
    @Json(name = "bitbucket")
    var bitbucket: List<Any>?,
    @Json(name = "github")
    var github: List<String>?
)