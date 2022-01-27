package com.playsdev.firsttest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.playsdev.firsttest.databinding.ItemRecyleBinding

class CoinAdapter() : ListAdapter<CoinData, CoinScheduleViewHolder>(DifUtilItemCallBack()) {
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

class DifUtilItemCallBack : DiffUtil.ItemCallback<CoinData>() {
    override fun areItemsTheSame(oldItem: CoinData, newItem: CoinData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CoinData, newItem: CoinData): Boolean {
        return oldItem.nameItem == newItem.nameItem && oldItem.nameFullItem == newItem.nameFullItem
                && oldItem.cost == newItem.cost && oldItem.image == newItem.image
    }
}

class CoinScheduleViewHolder(
    private val binding: ItemRecyleBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CoinData){
        binding.tvName.text = item.nameItem
        binding.tvFullName.text = item.nameFullItem
        binding.tvCost.text = item.cost
        binding.ivCoin.setImageResource(item.image)
    }
}
