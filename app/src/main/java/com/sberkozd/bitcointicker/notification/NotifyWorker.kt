package com.sberkozd.bitcointicker.notification

import android.content.Context
import androidx.annotation.NonNull
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sberkozd.bitcointicker.network.CoinService
import com.sberkozd.bitcointicker.ui.detail.DetailRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class NotifyWorker @AssistedInject constructor(
    @Assisted context: Context?,
    @Assisted params: WorkerParameters?,
    private val coinService: CoinService
) :
    CoroutineWorker(context!!, params!!) {


    @NonNull
    override suspend fun doWork(): Result {

        withContext(Dispatchers.Main) {
            val coinSymbol = inputData.getString("symbol")
            if (coinSymbol != null) {
                val coin = coinService.getCoinById(coinSymbol)

                coin.let {
                    NotificationHelper().sendFavoriteCoinPriceChangedNotification(
                        applicationContext,
                        name = "Ethereum",
                        symbol = coinSymbol
                    )
                }

            }

        }

        return Result.success()
    }
}
