package com.sberkozd.bitcointicker.ui.favorite

import com.sberkozd.bitcointicker.data.local.database.CoinDao
import com.sberkozd.bitcointicker.data.local.database.CoinEntity
import com.sberkozd.bitcointicker.network.NetworkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val coinDao: CoinDao
) : NetworkRepository {

    suspend fun removeFavorite(id: String) =
        coinDao.removeFavorite(id)

    suspend fun setFavorite(id: String) =
        coinDao.setFavorite(id)

    fun getCoinsFromDB(): Flow<List<CoinEntity>> =
        coinDao.getAllCoins()

    fun getFavoriteCoins(): Flow<List<CoinEntity>> =
        coinDao.getFavoriteCoins()
}
