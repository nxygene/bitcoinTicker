package com.sberkozd.bitcointicker.ui.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.*
import androidx.navigation.NavDeepLinkBuilder
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.sberkozd.bitcointicker.Event
import com.sberkozd.bitcointicker.MainActivity
import com.sberkozd.bitcointicker.R
import com.sberkozd.bitcointicker.data.local.database.CoinEntity
import com.sberkozd.bitcointicker.data.models.FirestoreModel
import com.sberkozd.bitcointicker.data.responses.CoinDetailResponse
import com.sberkozd.bitcointicker.data.responses.CoinGraphResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class DetailViewModel @Inject constructor(
    val state: SavedStateHandle,
    val repository: DetailRepository,
    val auth: FirebaseAuth,
    val firestore: FirebaseFirestore,
) : ViewModel() {

    private val coinEntity = state.get<CoinEntity>("coin")

    private val _coinDetailResponse: MutableSharedFlow<CoinDetailResponse?> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val coinDetailResponse: SharedFlow<CoinDetailResponse?> = _coinDetailResponse

    private val _coinGraphResponse: MutableStateFlow<CoinGraphResponse?> = MutableStateFlow(null)
    val coinGraphResponse: StateFlow<CoinGraphResponse?> = _coinGraphResponse

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    private val coinData = mutableListOf<Entry>()
    private val _lineDataSet = MutableLiveData(LineDataSet(coinData, CHART_LABEL))
    val lineDataSet: LiveData<LineDataSet> = _lineDataSet


    companion object {
        private const val CHART_LABEL = "COIN_CHART"
    }

    init {
        getCoinDetails()
        getCoinGraph()
    }

    private fun getCoinDetails() {
        coinEntity?.let {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getCoinDetail(
                    id = it.id,
                    onSuccess = {},
                    onError = {
                        launch(Dispatchers.IO) {
                            it?.let { eventChannel.send(Event.ShowToast(it)) }
                        }
                    })
                    .stateIn(viewModelScope)
                    .collect {
                        _coinDetailResponse.tryEmit(it)
                    }
            }
        }
    }

    private fun getCoinGraph() {
        coinEntity?.let {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getCoinChartById(
                    id = it.id,
                    onSuccess = {},
                    onError = {
                        viewModelScope.launch(Dispatchers.IO) {
                            it?.let { eventChannel.send(Event.ShowToast(it)) }
                        }
                    })
                    .stateIn(viewModelScope)
                    .collect {
                        _coinGraphResponse.value = it
                    }
            }
        }
    }

    private fun tickerFlow(period: Duration, initialDelay: Duration = Duration.ZERO) = flow {
        delay(initialDelay)
        while (true) {
            emit(Unit)
            delay(period)
        }
    }

    fun startAutoRefresh(interval: Int) {
        tickerFlow(interval.seconds)
            .onEach {
                getCoinDetails()
            }
            .launchIn(viewModelScope)
    }

    fun favoriteClicked(coin: CoinEntity, isFavorited: Boolean) {

        val firestoreModel = FirestoreModel(coin.id)
        viewModelScope.launch(Dispatchers.IO) {
            eventChannel.send(Event.ShowLoading)
            if (isFavorited) {
                firestore.collection("CoinModel")
                    .document(auth.currentUser?.email ?: "")
                    .update(firestoreModel.id!!, FieldValue.delete())
                    .addOnSuccessListener {
                        viewModelScope.launch(Dispatchers.IO) {
                            repository.removeFavorite(coin.id)
                        }
                        Log.e("Firestore", "Deleted!")
                    }.addOnFailureListener {
                        Log.e("Firestore", it.toString())
                    }
            } else {
                val model = hashMapOf(firestoreModel.id to firestoreModel)
                firestore.collection("CoinModel")
                    .document(auth.currentUser?.email ?: "")
                    .set(model, SetOptions.merge())
                    .addOnSuccessListener {
                        viewModelScope.launch(Dispatchers.IO) {
                            repository.setFavorite(coin.id)
                        }
                        Log.e("Firestore", "Added!")
                    }.addOnFailureListener {
                        Log.e("Firestore", it.toString())
                    }
            }
        }
    }
}