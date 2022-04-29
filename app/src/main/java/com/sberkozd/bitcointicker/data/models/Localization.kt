package com.sberkozd.bitcointicker.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Localization(
    @Json(name = "ar")
    var ar: String?,
    @Json(name = "bg")
    var bg: String?,
    @Json(name = "cs")
    var cs: String?,
    @Json(name = "da")
    var da: String?,
    @Json(name = "de")
    var de: String?,
    @Json(name = "el")
    var el: String?,
    @Json(name = "en")
    var en: String?,
    @Json(name = "es")
    var es: String?,
    @Json(name = "fi")
    var fi: String?,
    @Json(name = "fr")
    var fr: String?,
    @Json(name = "he")
    var he: String?,
    @Json(name = "hi")
    var hi: String?,
    @Json(name = "hr")
    var hr: String?,
    @Json(name = "hu")
    var hu: String?,
    @Json(name = "id")
    var id: String?,
    @Json(name = "it")
    var `it`: String?,
    @Json(name = "ja")
    var ja: String?,
    @Json(name = "ko")
    var ko: String?,
    @Json(name = "lt")
    var lt: String?,
    @Json(name = "nl")
    var nl: String?,
    @Json(name = "no")
    var no: String?,
    @Json(name = "pl")
    var pl: String?,
    @Json(name = "pt")
    var pt: String?,
    @Json(name = "ro")
    var ro: String?,
    @Json(name = "ru")
    var ru: String?,
    @Json(name = "sk")
    var sk: String?,
    @Json(name = "sl")
    var sl: String?,
    @Json(name = "sv")
    var sv: String?,
    @Json(name = "th")
    var th: String?,
    @Json(name = "tr")
    var tr: String?,
    @Json(name = "uk")
    var uk: String?,
    @Json(name = "vi")
    var vi: String?,
    @Json(name = "zh")
    var zh: String?,
    @Json(name = "zh-tw")
    var zhTw: String?
)