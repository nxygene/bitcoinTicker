package com.sberkozd.bitcointicker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.sberkozd.bitcointicker.*
import com.sberkozd.bitcointicker.databinding.FragmentHomeBinding
import com.sberkozd.bitcointicker.notification.NotificationHelper
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val notificationHelper = NotificationHelper()

        notificationHelper.createNotificationChannel(
            requireContext(),
            NotificationManagerCompat.IMPORTANCE_HIGH, true,
            getString(R.string.app_name), "App notification channel."
        )

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        with(binding) {
            val listAdapter = CoinListAdapter(
                favoriteListener = { coin, isFavorited ->
                    homeViewModel.favoriteClicked(coin, isFavorited)
                }, clickListener = { sharedElementView, coin ->
                    val extras = FragmentNavigatorExtras(sharedElementView to coin.id)
                    findNavController()
                        .navigate(
                            HomeFragmentDirections.actionHomeFragmentToDetailFragment(coin),
                            extras
                        )
                }
            )

            rvCoinList.apply {
                adapter = listAdapter
                postponeEnterTransition()
                viewTreeObserver
                    .addOnPreDrawListener {
                        startPostponedEnterTransition()
                        true
                    }
            }


            swipeRefreshLayout.setOnRefreshListener {
                homeViewModel.getAllCoinsFromNetwork()
            }

            if (homeViewModel.firestorefavoriteCoinIds != null) {
                homeViewModel.getAllCoinsFromNetwork(homeViewModel.firestorefavoriteCoinIds)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    homeViewModel.coinListResponse.collect { response ->
                        response?.let {
                            (rvCoinList.adapter as CoinListAdapter).submitList(it.toMutableList())
                        }
                        if (swipeRefreshLayout.isRefreshing) {
                            swipeRefreshLayout.isRefreshing = false
                        }
                    }
                }
            }

            fragmentHomeBtnLogout.setOnClickListener {
                homeViewModel.logout()
            }


            homeViewModel.eventsFlow.onEach {
                when (it) {
                    Event.Home.Logout -> {
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
                    }
                    Event.HideLoading -> {
                        mainViewModel.hideLoading()
                        if (binding.swipeRefreshLayout.isRefreshing) {
                            binding.swipeRefreshLayout.isRefreshing = false
                        }
                    }
                    Event.ShowLoading -> mainViewModel.showLoading()
                    is Event.ShowToast -> {
                        FancyToast.makeText(requireContext(),
                            it.text,
                            FancyToast.LENGTH_LONG,
                            FancyToast.WARNING,
                            false).show()
                    }

                }
            }.observeInLifecycle(viewLifecycleOwner)

            ivClearIcon.setOnClickListener {
                ivClearIcon.hide()
                etSearchView.text = null
            }

            etSearchView.textChanges()
                .debounce(400)
                .onEach {
                    if (it.isNullOrBlank()) {
                        ivClearIcon.hide()
                        homeViewModel.getAllCoinsFromDb()
                    } else {
                        homeViewModel.onSearchKeywordChanged(it.toString())
                        ivClearIcon.show()
                    }
                }
                .launchIn(lifecycleScope)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}