package com.sberkozd.bitcointicker.network

import com.sberkozd.bitcointicker.data.models.CoinModel
import com.sberkozd.bitcointicker.data.responses.CoinDetailResponse
import com.sberkozd.bitcointicker.data.responses.CoinGraphResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinService {
    @GET("coins/markets?vs_currency=usd")
    suspend fun getCoinList(): Response<List<CoinModel>>

    @GET("coins/{id}")
    suspend fun getCoinById(@Path("id") id: String): Response<CoinDetailResponse>

    @GET("coins/{id}/market_chart")
    suspend fun getCoinChartById(
        @Path("id") id: String,
        @Query("vs_currency") targetCurrency: String,
        @Query("days") days: Int
    ): Response<CoinGraphResponse>
}