package com.sberkozd.bitcointicker.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {

    @Query("SELECT * FROM coin_entity")
    fun getAllCoins(): Flow<List<CoinEntity>>

    @Query("SELECT * FROM coin_entity WHERE is_favorite = 1")
    fun getFavoriteCoins(): Flow<List<CoinEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllCoins(list: List<CoinEntity>)

    @Query("DELETE FROM coin_entity")
    suspend fun deleteAllData()

    @Query("UPDATE coin_entity SET is_favorite = 1 WHERE id = :id")
    suspend fun setFavorite(id: String)

    @Query("UPDATE coin_entity SET is_favorite = 0 WHERE id = :id")
    suspend fun removeFavorite(id: String)

    @Query("SELECT * FROM coin_entity where name like '%' || :keyword || '%' or symbol like '%' || :keyword || '%'")
    fun getCoinsByKeyword(keyword: String): Flow<List<CoinEntity>>

}