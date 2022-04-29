package com.sberkozd.bitcointicker.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TotalVolume(
    @Json(name = "aed")
    var aed: Long?,
    @Json(name = "ars")
    var ars: Long?,
    @Json(name = "aud")
    var aud: Long?,
    @Json(name = "bch")
    var bch: Int?,
    @Json(name = "bdt")
    var bdt: Long?,
    @Json(name = "bhd")
    var bhd: Long?,
    @Json(name = "bits")
    var bits: Long?,
    @Json(name = "bmd")
    var bmd: Long?,
    @Json(name = "bnb")
    var bnb: Int?,
    @Json(name = "brl")
    var brl: Long?,
    @Json(name = "btc")
    var btc: Int?,
    @Json(name = "cad")
    var cad: Long?,
    @Json(name = "chf")
    var chf: Long?,
    @Json(name = "clp")
    var clp: Long?,
    @Json(name = "cny")
    var cny: Long?,
    @Json(name = "czk")
    var czk: Long?,
    @Json(name = "dkk")
    var dkk: Long?,
    @Json(name = "dot")
    var dot: Int?,
    @Json(name = "eos")
    var eos: Long?,
    @Json(name = "eth")
    var eth: Int?,
    @Json(name = "eur")
    var eur: Long?,
    @Json(name = "gbp")
    var gbp: Long?,
    @Json(name = "hkd")
    var hkd: Long?,
    @Json(name = "huf")
    var huf: Long?,
    @Json(name = "idr")
    var idr: Long?,
    @Json(name = "ils")
    var ils: Long?,
    @Json(name = "inr")
    var inr: Long?,
    @Json(name = "jpy")
    var jpy: Long?,
    @Json(name = "krw")
    var krw: Long?,
    @Json(name = "kwd")
    var kwd: Long?,
    @Json(name = "link")
    var link: Long?,
    @Json(name = "lkr")
    var lkr: Long?,
    @Json(name = "ltc")
    var ltc: Int?,
    @Json(name = "mmk")
    var mmk: Long?,
    @Json(name = "mxn")
    var mxn: Long?,
    @Json(name = "myr")
    var myr: Long?,
    @Json(name = "ngn")
    var ngn: Long?,
    @Json(name = "nok")
    var nok: Long?,
    @Json(name = "nzd")
    var nzd: Long?,
    @Json(name = "php")
    var php: Long?,
    @Json(name = "pkr")
    var pkr: Long?,
    @Json(name = "pln")
    var pln: Long?,
    @Json(name = "rub")
    var rub: Long?,
    @Json(name = "sar")
    var sar: Long?,
    @Json(name = "sats")
    var sats: Long?,
    @Json(name = "sek")
    var sek: Long?,
    @Json(name = "sgd")
    var sgd: Long?,
    @Json(name = "thb")
    var thb: Long?,
    @Json(name = "try")
    var tryX: Long?,
    @Json(name = "twd")
    var twd: Long?,
    @Json(name = "uah")
    var uah: Long?,
    @Json(name = "usd")
    var usd: Long?,
    @Json(name = "vef")
    var vef: Long?,
    @Json(name = "vnd")
    var vnd: Long?,
    @Json(name = "xag")
    var xag: Int?,
    @Json(name = "xau")
    var xau: Int?,
    @Json(name = "xdr")
    var xdr: Long?,
    @Json(name = "xlm")
    var xlm: Long?,
    @Json(name = "xrp")
    var xrp: Long?,
    @Json(name = "yfi")
    var yfi: Int?,
    @Json(name = "zar")
    var zar: Long?
)