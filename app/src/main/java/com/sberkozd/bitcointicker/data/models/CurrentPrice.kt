package com.sberkozd.bitcointicker.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentPrice(
    @Json(name = "aed")
    var aed: Double?,
    @Json(name = "ars")
    var ars: Double?,
    @Json(name = "aud")
    var aud: Double?,
    @Json(name = "bch")
    var bch: Double?,
    @Json(name = "bdt")
    var bdt: Double?,
    @Json(name = "bhd")
    var bhd: Double?,
    @Json(name = "bits")
    var bits: Double?,
    @Json(name = "bmd")
    var bmd: Double?,
    @Json(name = "bnb")
    var bnb: Double?,
    @Json(name = "brl")
    var brl: Double?,
    @Json(name = "btc")
    var btc: Double?,
    @Json(name = "cad")
    var cad: Double?,
    @Json(name = "chf")
    var chf: Double?,
    @Json(name = "clp")
    var clp: Double?,
    @Json(name = "cny")
    var cny: Double?,
    @Json(name = "czk")
    var czk: Double?,
    @Json(name = "dkk")
    var dkk: Double?,
    @Json(name = "dot")
    var dot: Double?,
    @Json(name = "eos")
    var eos: Double?,
    @Json(name = "eth")
    var eth: Double?,
    @Json(name = "eur")
    var eur: Double?,
    @Json(name = "gbp")
    var gbp: Double?,
    @Json(name = "hkd")
    var hkd: Double?,
    @Json(name = "huf")
    var huf: Double?,
    @Json(name = "idr")
    var idr: Double?,
    @Json(name = "ils")
    var ils: Double?,
    @Json(name = "inr")
    var inr: Double?,
    @Json(name = "jpy")
    var jpy: Double?,
    @Json(name = "krw")
    var krw: Double?,
    @Json(name = "kwd")
    var kwd: Double?,
    @Json(name = "link")
    var link: Double?,
    @Json(name = "lkr")
    var lkr: Double?,
    @Json(name = "ltc")
    var ltc: Double?,
    @Json(name = "mmk")
    var mmk: Double?,
    @Json(name = "mxn")
    var mxn: Double?,
    @Json(name = "myr")
    var myr: Double?,
    @Json(name = "ngn")
    var ngn: Double?,
    @Json(name = "nok")
    var nok: Double?,
    @Json(name = "nzd")
    var nzd: Double?,
    @Json(name = "php")
    var php: Double?,
    @Json(name = "pkr")
    var pkr: Double?,
    @Json(name = "pln")
    var pln: Double?,
    @Json(name = "rub")
    var rub: Double?,
    @Json(name = "sar")
    var sar: Double?,
    @Json(name = "sats")
    var sats: Double?,
    @Json(name = "sek")
    var sek: Double?,
    @Json(name = "sgd")
    var sgd: Double?,
    @Json(name = "thb")
    var thb: Double?,
    @Json(name = "try")
    var tryX: Double?,
    @Json(name = "twd")
    var twd: Double?,
    @Json(name = "uah")
    var uah: Double?,
    @Json(name = "usd")
    var usd: Double?,
    @Json(name = "vef")
    var vef: Double?,
    @Json(name = "vnd")
    var vnd: Double?,
    @Json(name = "xag")
    var xag: Double?,
    @Json(name = "xau")
    var xau: Double?,
    @Json(name = "xdr")
    var xdr: Double?,
    @Json(name = "xlm")
    var xlm: Double?,
    @Json(name = "xrp")
    var xrp: Double?,
    @Json(name = "yfi")
    var yfi: Double?,
    @Json(name = "zar")
    var zar: Double?
)