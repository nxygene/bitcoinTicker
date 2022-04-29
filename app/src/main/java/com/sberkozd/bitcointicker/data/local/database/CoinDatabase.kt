package com.sberkozd.bitcointicker.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sberkozd.bitcointicker.dbVersion

@Database(
    version = dbVersion,
    entities = [CoinEntity::class]
)
abstract class CoinDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}