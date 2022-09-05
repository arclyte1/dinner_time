package com.example.dinner_time.presentation.restaurant_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dinner_time.domain.model.Restaurant
import com.example.dinner_time.databinding.RestaurantListItemBinding

class RestaurantListAdapter: ListAdapter<Restaurant, RestaurantListAdapter.ViewHolder>(
    object: DiffUtil.ItemCallback<Restaurant>() {
        override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem == newItem
        }

    }
) {

    var onClickListener: OnClickListener = OnClickListener {}

    inner class ViewHolder(binding: RestaurantListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val titleView = binding.textView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RestaurantListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = currentList[position]
        holder.titleView.text = item.title
        holder.root.setOnClickListener {
            onClickListener.onClick(item.id)
        }
    }

    class OnClickListener(val clickListener: (restaurantId: Long) -> Unit) {
        fun onClick(restaurantId: Long) = clickListener(restaurantId)
    }
}