package com.example.recipeapp.Activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.recipeapp.Models.Meal
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivityMealDetailsBinding
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

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


        // Set the initial status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryColor)



        // Get the meal data from the Intent
        val meal = intent.getSerializableExtra("meal_data") as? Meal

        if (meal != null) {
            binding.mealDetails.text = meal.strInstructions
//            binding.collapsingToolbar.title = meal.strMeal
            Glide.with(this).load(meal.strMealThumb).into(binding.toolbarImg)
        }

        // Set initial title visibility
        binding.collapsingToolbar.title = ""

        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            if (abs(verticalOffset) == binding.appBarLayout.totalScrollRange) {
                // Collapsed state: show title
                binding.collapsingToolbar.title = meal?.strMeal
            } else {
                // Expanded state: hide title
                binding.collapsingToolbar.title = ""
            }
        })
    }
}