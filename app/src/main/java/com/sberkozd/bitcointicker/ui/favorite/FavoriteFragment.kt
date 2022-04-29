package com.sberkozd.bitcointicker.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.sberkozd.bitcointicker.Event
import com.sberkozd.bitcointicker.MainViewModel
import com.sberkozd.bitcointicker.databinding.FragmentFavoriteBinding
import com.sberkozd.bitcointicker.observeInLifecycle
import com.sberkozd.bitcointicker.ui.home.CoinListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentFavoriteBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        with(binding) {
            val listAdapter = CoinListAdapter(
                favoriteListener = { coin, isFavorited ->
                    favoriteViewModel.favoriteClicked(coin, isFavorited)
                }, clickListener = { sharedElementView, coin ->
                    val extras = FragmentNavigatorExtras(sharedElementView to coin.id)
                    findNavController()
                        .navigate(
                            FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(coin),
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
                favoriteViewModel.getAllFavoriteCoinsFromFirestore()
            }

            if (favoriteViewModel.fetchedDataFromFirestore == true) {
                favoriteViewModel.getAllFavoriteCoinsFromFirestore()
            }


            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    favoriteViewModel.favoriteListResponse.collect { response ->
                        response?.let {
                            (rvCoinList.adapter as CoinListAdapter).submitList(it.toMutableList())
                        }
                        if (swipeRefreshLayout.isRefreshing) {
                            swipeRefreshLayout.isRefreshing = false
                        }
                    }
                }
            }

            favoriteViewModel.eventsFlow.onEach {
                when (it) {
                    Event.HideLoading -> mainViewModel.hideLoading()
                    Event.ShowLoading -> mainViewModel.showLoading()
                }
            }.observeInLifecycle(viewLifecycleOwner)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}