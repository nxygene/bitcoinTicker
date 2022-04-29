package com.sberkozd.bitcointicker.data.local.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import org.jetbrains.annotations.NotNull

@Entity(tableName = "coin_entity")
@Parcelize
data class CoinEntity(
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "symbol")
    @Json(name = "symbol")
    var symbol: String,
    @ColumnInfo(name = "ath")
    @Json(name = "ath")
    var ath: Double?,
    @ColumnInfo(name = "ath_change_percentage")
    @Json(name = "ath_change_percentage")
    var athChangePercentage: Double?,
    @ColumnInfo(name = "ath_date")
    @Json(name = "ath_date")
    var athDate: String?,
    @ColumnInfo(name = "atl")
    @Json(name = "atl")
    var atl: Double?,
    @ColumnInfo(name = "atl_change_percentage")
    @Json(name = "atl_change_percentage")
    var atlChangePercentage: Double?,
    @ColumnInfo(name = "atl_date")
    @Json(name = "atl_date")
    var atlDate: String?,
    @ColumnInfo(name = "circulating_supply")
    @Json(name = "circulating_supply")
    var circulatingSupply: Double?,
    @ColumnInfo(name = "current_price")
    @Json(name = "current_price")
    var currentPrice: Double?,
    @ColumnInfo(name = "fully_diluted_valuation")
    @Json(name = "fully_diluted_valuation")
    var fullyDilutedValuation: Long?,
    @ColumnInfo(name = "high_24h")
    @Json(name = "high_24h")
    var high24h: Double?,
    @ColumnInfo(name = "id")
    @Json(name = "id")
    var id: String,
    @ColumnInfo(name = "image")
    @Json(name = "image")
    var image: String?,
    @ColumnInfo(name = "last_updated")
    @Json(name = "last_updated")
    var lastUpdated: String?,
    @ColumnInfo(name = "low_24h")
    @Json(name = "low_24h")
    var low24h: Double?,
    @ColumnInfo(name = "market_cap")
    @Json(name = "market_cap")
    var marketCap: Long?,
    @ColumnInfo(name = "market_cap_change_24h")
    @Json(name = "market_cap_change_24h")
    var marketCapChange24h: Double?,
    @ColumnInfo(name = "market_cap_change_percentage_24h")
    @Json(name = "market_cap_change_percentage_24h")
    var marketCapChangePercentage24h: Double?,
    @ColumnInfo(name = "market_cap_rank")
    @Json(name = "market_cap_rank")
    var marketCapRank: Int?,
    @ColumnInfo(name = "max_supply")
    @Json(name = "max_supply")
    var maxSupply: Double?,
    @ColumnInfo(name = "name")
    @Json(name = "name")
    var name: String?,
    @ColumnInfo(name = "price_change_24h")
    @Json(name = "price_change_24h")
    var priceChange24h: Double?,
    @ColumnInfo(name = "price_change_percentage_24h")
    @Json(name = "price_change_percentage_24h")
    var priceChangePercentage24h: Double?,
    @ColumnInfo(name = "total_supply")
    @Json(name = "total_supply")
    var totalSupply: Double?,
    @ColumnInfo(name = "total_volume")
    @Json(name = "total_volume")
    var totalVolume: Double?,
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Int? = null,
) : Parcelable