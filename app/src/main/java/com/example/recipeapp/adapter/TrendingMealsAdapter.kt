package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.Models.Meal
import com.example.recipeapp.databinding.ItemTrendingNowBinding

class TrendingMealsAdapter(private val items: List<Meal>,     private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<TrendingMealsAdapter.TrendingViewHolder>(){


        // Define an interface for item clicks
        interface OnItemClickListener {
            fun onItemClick(meal: Meal)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
            val binding = ItemTrendingNowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TrendingViewHolder(binding)
        }

        override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
            val item = items[position]
            // Bind data (e.g., set an image or text)
            Glide.with(holder.itemView.context)
                .load(item.strMealThumb)  // Assuming 'item' is the image URL
                .into(holder.binding.trendingImageView)

            // Set up click listener for each item
            holder.itemView.setOnClickListener {
                itemClickListener.onItemClick(item)
            }
        }

        override fun getItemCount(): Int = items.size

        inner class TrendingViewHolder(val binding: ItemTrendingNowBinding) : RecyclerView.ViewHolder(binding.root)
}
