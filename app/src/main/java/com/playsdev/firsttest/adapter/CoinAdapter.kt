package com.playsdev.firsttest.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.playsdev.firsttest.data.Coin
import com.playsdev.firsttest.databinding.ItemRecyleBinding

class CoinAdapter(
    private val onClickListener: OnClickListener
) : PagingDataAdapter<Coin, CoinScheduleViewHolder>(DifUtilItemCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinScheduleViewHolder {
        return CoinScheduleViewHolder(
            ItemRecyleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CoinScheduleViewHolder, position: Int) {
        holder.bind(getItem(position)!!,onClickListener)

    }
}

class DifUtilItemCallBack : DiffUtil.ItemCallback<Coin>() {
    override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem.id == newItem.id && oldItem.symbol == newItem.symbol
                && oldItem.current_price == newItem.current_price && oldItem.image == newItem.image
    }
}

class CoinScheduleViewHolder(
    private val binding: ItemRecyleBinding,
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(item: Coin,onClickListener: OnClickListener) {
        binding.tvName.text = item.id
        binding.tvFullName.text = item.symbol
        binding.tvCost.text = item.current_price.toString()
        binding.ivCoin.load(item.image)
        itemView.setOnClickListener {
            onClickListener.onClick(item,binding.ivCoin,binding.tvFullName,binding.tvCost)
        }
    }

}

class OnClickListener(val clickListener: (Coin, ImageView, TextView,TextView) -> Unit) {
    fun onClick(
        coin: Coin,
        icon: ImageView,
        title: TextView,
        cost:TextView
    ) = clickListener(coin,icon,title,cost)
}


