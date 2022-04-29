package com.sberkozd.bitcointicker.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarketData(
//    @Json(name = "ath")
//    var ath: Ath?,
    @Json(name = "ath_change_percentage")
    var athChangePercentage: AthChangePercentage?,
    @Json(name = "ath_date")
    var athDate: AthDate?,
//    @Json(name = "atl")
//    var atl: Atl?,
    @Json(name = "atl_change_percentage")
    var atlChangePercentage: AtlChangePercentage?,
    @Json(name = "atl_date")
    var atlDate: AtlDate?,
    @Json(name = "circulating_supply")
    var circulatingSupply: Double?,
    @Json(name = "current_price")
    var currentPrice: CurrentPrice?,
    @Json(name = "fdv_to_tvl_ratio")
    var fdvToTvlRatio: Any?,
    @Json(name = "fully_diluted_valuation")
    var fullyDilutedValuation: FullyDilutedValuation?,
//    @Json(name = "high_24h")
//    var high24h: High24h?,
    @Json(name = "last_updated")
    var lastUpdated: String?,
//    @Json(name = "low_24h")
//    var low24h: Low24h?,
//    @Json(name = "market_cap")
//    var marketCap: MarketCap?,
    @Json(name = "market_cap_change_24h")
    var marketCapChange24h: Double?,
//    @Json(name = "market_cap_change_24h_in_currency")
//    var marketCapChange24hInCurrency: MarketCapChange24hInCurrency?,
    @Json(name = "market_cap_change_percentage_24h")
    var marketCapChangePercentage24h: Double?,
    @Json(name = "market_cap_rank")
    var marketCapRank: Int?,
    @Json(name = "max_supply")
    var maxSupply: Double?,
    @Json(name = "mcap_to_tvl_ratio")
    var mcapToTvlRatio: Any?,
    @Json(name = "price_change_24h")
    var priceChange24h: Double?,
//    @Json(name = "price_change_24h_in_currency")
//    var priceChange24hInCurrency: PriceChange24hInCurrency?,
    @Json(name = "price_change_percentage_14d")
    var priceChangePercentage14d: Double?,
//    @Json(name = "price_change_percentage_14d_in_currency")
//    var priceChangePercentage14dInCurrency: PriceChangePercentage14dInCurrency?,
//    @Json(name = "price_change_percentage_1h_in_currency")
//    var priceChangePercentage1hInCurrency: PriceChangePercentage1hInCurrency?,
    @Json(name = "price_change_percentage_1y")
    var priceChangePercentage1y: Double?,
//    @Json(name = "price_change_percentage_1y_in_currency")
//    var priceChangePercentage1yInCurrency: PriceChangePercentage1yInCurrency?,
    @Json(name = "price_change_percentage_200d")
    var priceChangePercentage200d: Double?,
//    @Json(name = "price_change_percentage_200d_in_currency")
//    var priceChangePercentage200dInCurrency: PriceChangePercentage200dInCurrency?,
    @Json(name = "price_change_percentage_24h")
    var priceChangePercentage24h: Double?,
//    @Json(name = "price_change_percentage_24h_in_currency")
//    var priceChangePercentage24hInCurrency: PriceChangePercentage24hInCurrency?,
    @Json(name = "price_change_percentage_30d")
    var priceChangePercentage30d: Double?,
//    @Json(name = "price_change_percentage_30d_in_currency")
//    var priceChangePercentage30dInCurrency: PriceChangePercentage30dInCurrency?,
    @Json(name = "price_change_percentage_60d")
    var priceChangePercentage60d: Double?,
//    @Json(name = "price_change_percentage_60d_in_currency")
//    var priceChangePercentage60dInCurrency: PriceChangePercentage60dInCurrency?,
    @Json(name = "price_change_percentage_7d")
    var priceChangePercentage7d: Double?,
//    @Json(name = "price_change_percentage_7d_in_currency")
//    var priceChangePercentage7dInCurrency: PriceChangePercentage7dInCurrency?,
    @Json(name = "roi")
    var roi: Any?,
    @Json(name = "total_supply")
    var totalSupply: Double?,
    @Json(name = "total_value_locked")
    var totalValueLocked: Any?,
//    @Json(name = "total_volume")
//    var totalVolume: TotalVolume?
)