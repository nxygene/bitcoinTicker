package com.sberkozd.bitcointicker.ui.detail

import com.sberkozd.bitcointicker.data.local.database.CoinDao
import com.sberkozd.bitcointicker.data.responses.CoinDetailResponse
import com.sberkozd.bitcointicker.data.responses.CoinGraphResponse
import com.sberkozd.bitcointicker.network.CoinService
import com.sberkozd.bitcointicker.network.NetworkRepository
import com.sberkozd.bitcointicker.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val coinService: CoinService,
    private val coinDao: CoinDao
) :
    NetworkRepository {

    fun getCoinDetail(
        id: String,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit,
    ) = flow {

        val result: NetworkResult<CoinDetailResponse> = wrapNetworkResult({
            coinService.getCoinById(id)
        })

        when (result) {
            is NetworkResult.SuccessfulNetworkResult -> {
                emit(result.body)
                onSuccess()
            }
            is NetworkResult.ErrorNetworkResult -> {
                onError("Check internet connection")
            }
            is NetworkResult.EmptyNetworkResult -> {
                onError("No data found")
            }
            is NetworkResult.ExceptionResult -> {
                onError(result.errorMessage)
            }
        }

    }.flowOn(Dispatchers.IO)

    fun getCoinChartById(
        id: String,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit,
    ) = flow {

        val result: NetworkResult<CoinGraphResponse> = wrapNetworkResult({
            coinService.getCoinChartById(id, "usd", 1)
        })

        when (result) {
            is NetworkResult.SuccessfulNetworkResult -> {
                emit(result.body)
                onSuccess()
            }
            is NetworkResult.ErrorNetworkResult -> {
                onError("Check internet connection")
            }
            is NetworkResult.EmptyNetworkResult -> {
                onError("No data found")
            }
            is NetworkResult.ExceptionResult -> {
                onError(result.errorMessage)
            }
        }

    }.flowOn(Dispatchers.IO)

    suspend fun removeFavorite(id: String) {
        coinDao.removeFavorite(id)
    }

    suspend fun setFavorite(id: String) {
        coinDao.setFavorite(id)
    }

}