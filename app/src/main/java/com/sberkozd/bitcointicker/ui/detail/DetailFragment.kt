package com.sberkozd.bitcointicker.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import androidx.transition.TransitionInflater
import coil.load
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sberkozd.bitcointicker.*
import com.sberkozd.bitcointicker.databinding.BottomSheetBinding
import com.sberkozd.bitcointicker.databinding.FragmentDetailBinding
import com.sberkozd.bitcointicker.notification.NotificationHelper
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val detailViewModel: DetailViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()

    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!

    private var chartStyle: LineStyle? = null

    private var refreshInterval: Int = 5000

    private var graphColor: Int? = null

    var firstPrice: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTransitionName(binding.ivCoin, args.coin.id)

        val animation = TransitionInflater.from(
            requireContext()
        ).inflateTransition(
            android.R.transition.move
        )

        val notificationHelper = NotificationHelper()

        notificationHelper.createNotificationChannel(
            requireContext(),
            NotificationManagerCompat.IMPORTANCE_HIGH, true,
            getString(R.string.app_name), "App notification channel."
        )

        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation


        with(binding) {
            tvCoinName.text = args.coin.name
            ivCoin.load(args.coin.image) {
                allowHardware(false)
                listener(onSuccess = { request, result ->
                    Palette.Builder(result.drawable.toBitmap()).generate {
                        it?.let { palette ->
                            graphColor = palette.getDominantColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.gold
                                )
                            )
                        }
                    }
                })
            }
            tvCoinPercentage.text =
                args.coin.priceChangePercentage24h?.formatPercentage().toString()
            tvMarketPrice.text = ("$${args.coin.currentPrice}")
            tvLastUpdated.text = ("Last updated ${args.coin.lastUpdated?.formatDate()} ")
            btnFavorite.isChecked = args.coin.isFavorite == 1
            btnFavorite.setOnClickListener {
                detailViewModel.favoriteClicked(args.coin, !btnFavorite.isChecked)
            }
        }

        collectState(detailViewModel.coinDetailResponse) { response ->
            response?.let {
                with(binding) {
                    tvRefreshInterval.setOnClickListener {
                        showBottomSheetDialog()
                    }
                    tvCoinName.text = response.name
                    ivCoin.load(response.image?.large)
                    response.hashingAlgorithm?.let {
                        tvHashingAlgorithm.text = "Hashing Algorithm : $it"
                    }
                    tvCoinPercentage.text =
                        response.marketData?.priceChangePercentage24h?.formatPercentage()
                            .toString()
                    tvMarketPrice.text =
                        ("$" + response.marketData?.currentPrice?.usd.toString())

                    tvLastUpdated.text = ("Last updated ${response.lastUpdated?.formatDate()} ")

                }

                if (firstPrice != response.marketData?.currentPrice?.usd?.toInt() ?: 0) {
                    NotificationHelper().sendFavoriteCoinPriceChangedNotification(
                        requireContext(),
                        response.name.toString()+ " has been updated!",
                        response.symbol.toString()
                    )
                }
                firstPrice = response.marketData?.currentPrice?.usd?.toInt()!!
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.coinGraphResponse.collect { response ->
                    response?.let {

                        val list = mutableListOf<Entry>()
                        val xValues = mutableListOf<String>()

                        val sdf = SimpleDateFormat("yyyy-MM-dd")

                        it.coinChange.forEachIndexed { index, doubles ->
                            val date = Date(doubles[0].toLong())
                            val label = sdf.format(date)

                            xValues.add(label)
                            list.add(Entry(index.toFloat(), doubles[1].toFloat()))

                        }

                        val lineDataSet = LineDataSet(list, "Price in 24H")

                        chartStyle = LineStyle(requireContext())
                        graphColor?.let {
                            chartStyle?.styleLineDataSet(lineDataSet, it)
                        }
                        binding.lineChart.data = LineData(lineDataSet)
                        binding.lineChart.invalidate()

                        binding.lineChart.apply {
                            axisLeft.textColor = Color.WHITE
                            axisRight.textColor = Color.WHITE
                            legend.textColor = Color.WHITE
                            xAxis.textColor = Color.WHITE

                            setPinchZoom(false)

                            description = null
                            legend.isEnabled = false
                        }
                    }
                }
            }
        }
    }


    private fun showBottomSheetDialog() {
        val bottomSheetDialog =
            BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        val inflater = LayoutInflater.from(requireContext())
        val dialogBinding = BottomSheetBinding.inflate(inflater)
        bottomSheetDialog.apply {
            dialogBinding.apply {
                setContentView(root)
                btnUpdate.setOnClickListener {
                    if (refreshInterval != slider.value.toInt()) {
                        refreshInterval = slider.value.toInt()
                        FancyToast.makeText(
                            requireContext(),
                            "Refresh Interval changed to ${slider.value.toInt()} !",
                            FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false
                        ).show()
                        detailViewModel.startAutoRefresh(slider.value.toInt())
                        dismiss()
                    } else {
                        FancyToast.makeText(
                            requireContext(),
                            "Refresh Interval is already ${slider.value.toInt()} !",
                            FancyToast.LENGTH_LONG, FancyToast.WARNING, false
                        ).show()
                    }
                }
            }
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}