package com.sberkozd.bitcointicker.data.responses


import com.sberkozd.bitcointicker.data.models.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoinDetailResponse(
    @Json(name = "additional_notices")
    var additionalNotices: List<Any>?,
    @Json(name = "asset_platform_id")
    var assetPlatformId: Any?,
    @Json(name = "block_time_in_minutes")
    var blockTimeInMinutes: Int?,
    @Json(name = "categories")
    var categories: List<String>?,
    @Json(name = "coingecko_rank")
    var coingeckoRank: Int?,
    @Json(name = "coingecko_score")
    var coingeckoScore: Double?,
    @Json(name = "community_data")
    var communityData: CommunityData?,
    @Json(name = "community_score")
    var communityScore: Double?,
    @Json(name = "country_origin")
    var countryOrigin: String?,
    @Json(name = "description")
    var description: Description?,
    @Json(name = "developer_data")
    var developerData: DeveloperData?,
    @Json(name = "developer_score")
    var developerScore: Double?,
    @Json(name = "genesis_date")
    var genesisDate: String?,
    @Json(name = "hashing_algorithm")
    var hashingAlgorithm: String?,
    @Json(name = "id")
    var id: String,
    @Json(name = "image")
    var image: Image?,
    @Json(name = "last_updated")
    var lastUpdated: String?,
    @Json(name = "links")
    var links: Links?,
    @Json(name = "liquidity_score")
    var liquidityScore: Double?,
    @Json(name = "localization")
    var localization: Localization?,
    @Json(name = "market_cap_rank")
    var marketCapRank: Int?,
    @Json(name = "market_data")
    var marketData: MarketData?,
    @Json(name = "name")
    var name: String?,
    @Json(name = "platforms")
    var platforms: Platforms?,
    @Json(name = "public_interest_score")
    var publicInterestScore: Int?,
    @Json(name = "public_interest_stats")
    var publicInterestStats: PublicInterestStats?,
    @Json(name = "public_notice")
    var publicNotice: Any?,
    @Json(name = "sentiment_votes_down_percentage")
    var sentimentVotesDownPercentage: Double?,
    @Json(name = "sentiment_votes_up_percentage")
    var sentimentVotesUpPercentage: Double?,
    @Json(name = "status_updates")
    var statusUpdates: List<Any>?,
    @Json(name = "symbol")
    var symbol: String?
)