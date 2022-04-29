package com.sberkozd.bitcointicker.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sberkozd.bitcointicker.R
import com.sberkozd.bitcointicker.data.local.database.CoinEntity
import com.sberkozd.bitcointicker.databinding.ItemCoinBinding
import com.sberkozd.bitcointicker.formatPercentage

class CoinListAdapter(
    val favoriteListener: (coin: CoinEntity, isFavorited: Boolean) -> Unit,
    val clickListener: (sharedElementView: ImageView, coin: CoinEntity) -> Unit,
) :
    ListAdapter<CoinEntity, CoinListAdapter.CoinViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val binding = ItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinViewHolder(binding)
    }

    override fun submitList(list: MutableList<CoinEntity>?) {
        super.submitList(list?.map { it.copy() }?.toMutableList())
    }


    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.binding.apply {
            tvCoinName.text = getItem(position).name
            tvCoinSymbol.text = getItem(position).symbol.uppercase()
            tvCoinPercentage.text = getItem(position).priceChangePercentage24h?.formatPercentage()
            ivCoin.load(getItem(position).image)
            ViewCompat.setTransitionName(ivCoin,getItem(position).id)
            btnFavorite.isChecked = getItem(position).isFavorite == 1
            tvMarketPrice.text = "$" + getItem(position).currentPrice.toString()
            root.setOnClickListener {
                clickListener(ivCoin, getItem(holder.adapterPosition))
            }

            getItem(position).marketCapChangePercentage24h?.let { percentage ->
                when {
                    percentage < 0.0 -> {
                        tvCoinPercentage.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            R.drawable.ic_arrow_loss,
                            0, 0, 0
                        )
                    }

                    percentage == 0.0 -> {
                        tvCoinPercentage.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            R.drawable.ic_arrow_profit,
                            0, 0, 0
                        )
                    }
                    percentage > 0.0 -> {
                        tvCoinPercentage.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            R.drawable.ic_arrow_profit,
                            0, 0, 0
                        )
                    }
                }

            }
            btnFavorite.setOnClickListener {
                onFavoriteClicked(
                    getItem(holder.adapterPosition),
                    getItem(holder.adapterPosition).isFavorite == 1
                )
            }
        }
    }

    private fun onFavoriteClicked(coin: CoinEntity, isFavorited: Boolean) {
        favoriteListener(coin, isFavorited)
    }

    class CoinViewHolder(val binding: ItemCoinBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<CoinEntity> =
            object : DiffUtil.ItemCallback<CoinEntity>() {
                override fun areItemsTheSame(oldItem: CoinEntity, newItem: CoinEntity) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: CoinEntity,
                    newItem: CoinEntity,
                ) = oldItem == newItem
            }
    }
}
