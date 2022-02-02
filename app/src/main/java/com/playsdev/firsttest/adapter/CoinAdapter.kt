package com.playsdev.firsttest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.playsdev.firsttest.cloud.CoinResponce
import com.playsdev.firsttest.databinding.ItemRecyleBinding

class CoinAdapter() : ListAdapter<CoinResponce, CoinScheduleViewHolder>(DifUtilItemCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinScheduleViewHolder {
        return CoinScheduleViewHolder(
            ItemRecyleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CoinScheduleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DifUtilItemCallBack : DiffUtil.ItemCallback<CoinResponce>() {
    override fun areItemsTheSame(oldItem: CoinResponce, newItem: CoinResponce): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CoinResponce, newItem: CoinResponce): Boolean {
        return oldItem.id == newItem.id && oldItem.symbol == newItem.symbol
                && oldItem.current_price == newItem.current_price && oldItem.image == newItem.image
    }
}

class CoinScheduleViewHolder(
    private val binding: ItemRecyleBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CoinResponce){
        binding.tvName.text = item.id
        binding.tvFullName.text = item.symbol
        binding.tvCost.text = item.current_price.toString()
        binding.ivCoin.load(item.image)

    }
}
