package com.example.recipeapp.Activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.recipeapp.Models.Meal
import com.example.recipeapp.Models.MealX
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivityMealDetailsBinding

class MealDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMealDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get the meal data from the Intent
        val meal = intent.getSerializableExtra("meal_data") as? MealX

        if (meal != null) {
            binding.mealDetails.text = meal.strInstructions
            binding.collapsingToolbar.title = meal.strMeal
            Glide.with(this).load(meal.strMealThumb).into(binding.toolbarImg)
        }
    }
}