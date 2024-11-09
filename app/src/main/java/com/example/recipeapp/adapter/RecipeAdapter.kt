package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.R
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.Models.Meal
import com.example.recipeapp.databinding.ItemRecipeBinding


class RecipeAdapter(private val mealList: List<Meal>,     private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecipeAdapter.MealViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(meal: Meal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = mealList[position]
        holder.bind(meal)
    }

    override fun getItemCount(): Int = mealList.size

    inner class MealViewHolder(private val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            // Set the meal name
            binding.tvMealName.text = meal.strMeal

            // Load the image with Glide
            Glide.with(binding.root.context)
                .load(meal.strMealThumb)
                .into(binding.ivMealThumb)

            // Set the click listener on the root of the CardView
            binding.root.setOnClickListener {
                listener.onItemClick(meal)
            }
        }
    }
}
