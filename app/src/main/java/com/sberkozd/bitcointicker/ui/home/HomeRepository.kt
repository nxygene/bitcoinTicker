package com.sberkozd.bitcointicker.ui.home

import com.sberkozd.bitcointicker.data.local.database.CoinDao
import com.sberkozd.bitcointicker.data.local.database.CoinEntity
import com.sberkozd.bitcointicker.data.models.CoinModel
import com.sberkozd.bitcointicker.network.CoinService
import com.sberkozd.bitcointicker.network.NetworkRepository
import com.sberkozd.bitcointicker.network.NetworkResult
import com.sberkozd.bitcointicker.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val coinService: CoinService,
    private val coinDao: CoinDao,
) : NetworkRepository {

    fun getCoins(
        favoriteCoinIds: Set<String>? = null,
        onSuccess: suspend () -> Unit,
        onError: suspend (String?) -> Unit,
    ) = flow {

        val result: NetworkResult<List<CoinModel>> = wrapNetworkResult({
            coinService.getCoinList()
        })

        when (result) {
            is NetworkResult.SuccessfulNetworkResult -> {
                if (favoriteCoinIds != null) {
                    coinDao.insertAllCoins(
                        result.body
                            .map { it.toEntity() }
                            .onEach {
                                if (it.id in favoriteCoinIds) {
                                    it.isFavorite = 1
                                }
                            }
                    )
                } else {
                    coinDao.insertAllCoins(result.body.map { it.toEntity() })
                }

                coinDao.getAllCoins().collect {
                    emit(it)
                    onSuccess()
                }
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

    fun searchCoins(keyword: String): Flow<List<CoinEntity>> = coinDao.getCoinsByKeyword(keyword)


    suspend fun removeFavorite(id: String) =
        coinDao.removeFavorite(id)

    suspend fun setFavorite(id: String) =
        coinDao.setFavorite(id)

    fun getCoinsFromDB(): Flow<List<CoinEntity>> =
        coinDao.getAllCoins()

    suspend fun deleteAllData() = coinDao.deleteAllData()
}