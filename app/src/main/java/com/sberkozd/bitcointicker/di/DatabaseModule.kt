package com.sberkozd.bitcointicker.di

import android.content.Context
import androidx.room.Room
import com.sberkozd.bitcointicker.data.local.database.CoinDao
import com.sberkozd.bitcointicker.data.local.database.CoinDatabase
import com.sberkozd.bitcointicker.database_name
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): CoinDatabase {
        return Room.databaseBuilder(context, CoinDatabase::class.java, database_name)
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(database: CoinDatabase): CoinDao {
        return database.coinDao()
    }
}