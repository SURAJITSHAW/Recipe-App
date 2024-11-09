package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.Models.Category
import com.example.recipeapp.databinding.ItemCategoryBinding

class CategoryAdapter(private val items: List<Category>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){


        // Define an interface for item clicks
//        interface OnItemClickListener {
//            fun onItemClick(category: Category)
//        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
            val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CategoryViewHolder(binding)
        }

        override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
            val item = items[position]
            // Bind data (e.g., set an image or text)
            Glide.with(holder.itemView.context)
                .load(item.strCategoryThumb)  // Assuming 'item' is the image URL
                .into(holder.binding.categoryImageView)

            holder.binding.categoryTextView.text = item.strCategory

            // Set up click listener for each item
//            holder.itemView.setOnClickListener {
//                itemClickListener.onItemClick(item)
//            }
        }

        override fun getItemCount(): Int = items.size

        inner class CategoryViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)
}
