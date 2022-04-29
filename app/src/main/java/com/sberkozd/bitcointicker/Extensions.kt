package com.sberkozd.bitcointicker

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.annotation.CheckResult
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.sberkozd.bitcointicker.data.local.database.CoinEntity
import com.sberkozd.bitcointicker.data.models.CoinModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.pow
import kotlin.math.roundToInt

class FlowObserver<T>(
    lifecycleOwner: LifecycleOwner,
    private val flow: Flow<T>,
    private val collector: suspend (T) -> Unit,
) {

    private var job: Job? = null

    init {
        lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { source: LifecycleOwner, event: Lifecycle.Event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    job = source.lifecycleScope.launch {
                        flow.collect { collector(it) }
                    }
                }
                Lifecycle.Event.ON_STOP -> {
                    job?.cancel()
                    job = null
                }
                else -> {
                }
            }
        })
    }
}

inline fun <reified T> Flow<T>.observeInLifecycle(
    lifecycleOwner: LifecycleOwner,
) = FlowObserver(lifecycleOwner, this, {})


fun <State> Fragment.collectState(stateFlow: Flow<State>, onState: (State) -> Unit) {
    lifecycleScope.launch {
        stateFlow
            .flowWithLifecycle(lifecycle)
            .collectLatest { state -> if (state != null) onState(state) }
    }
}


fun Double.formatPercentage(): String {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.DOWN
    val roundoff = String.format("%.2f", roundTo(2))
    return "$roundoff%"
}


fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}


fun CoinModel.toEntity(): CoinEntity {
    return CoinEntity(
        symbol,
        ath,
        athChangePercentage,
        athDate,
        atl,
        atlChangePercentage,
        atlDate,
        circulatingSupply,
        currentPrice,
        fullyDilutedValuation,
        high24h,
        id,
        image,
        lastUpdated,
        low24h,
        marketCap,
        marketCapChange24h,
        marketCapChangePercentage24h,
        marketCapRank,
        maxSupply,
        name,
        priceChange24h,
        priceChangePercentage24h,
        totalSupply,
        totalVolume
    )
}

@CheckResult
fun EditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow<CharSequence?> {
        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySend(s)
            }
        }
        addTextChangedListener(listener)
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}

fun View.hide() {
    if (visibility != View.GONE)
        visibility = View.GONE
}

fun View.show() {
    if (visibility != View.VISIBLE)
        visibility = View.VISIBLE
}

fun String.formatDate(): String {
    return DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSS").format(ZonedDateTime.parse(this))
}