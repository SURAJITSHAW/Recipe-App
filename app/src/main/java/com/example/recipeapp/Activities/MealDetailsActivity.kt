package com.example.recipeapp.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.recipeapp.Fragments.HomeFragment
import com.example.recipeapp.Models.Meal
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivityMealDetailsBinding
import com.example.recipeapp.viewModels.MealDetailsViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class MealDetailsActivity : AppCompatActivity() {
    private val viewModel: MealDetailsViewModel by viewModels()

    private lateinit var binding: ActivityMealDetailsBinding
    private lateinit var mealId: String
    private lateinit var mealImgUrl: String
    private var mealDetails: Meal? = null


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

//        get intent data
        getIntentData()
//        call to viewmodel for meal details
        viewModel.getMealDetails(mealId)
//        observe the meal details livedata
        observeMealDetailsLivedata()

        setupCollapsingToolbar()

    }

    private fun observeMealDetailsLivedata() {
        viewModel.mealDetailsLivedata.observe(this, Observer { meal ->
            if (meal != null) {
                mealDetails = meal
                binding.mealDetails.text = meal.strInstructions
                binding.btnYt.visibility = View.VISIBLE
                binding.btnYt.setOnClickListener {
                    val url = meal.strYoutube
                    if (url.isNotEmpty()) {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(this, "Unable to open link", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "YouTube link unavailable!", Toast.LENGTH_SHORT).show()
                    }
                }

                binding.collapsingToolbar.title = meal.strMeal
                binding.mealCategory.text = "Category : ${meal.strCategory}"
                binding.mealLocation.text = "Location : ${meal.strArea}"
                binding.linearProgressBar.visibility = View.GONE
            }
        })

    }

    private fun getIntentData() {
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID).toString()
        mealImgUrl = intent.getStringExtra(HomeFragment.IMG_URL).toString()

        if (mealImgUrl != null) {
            Glide.with(this).load(mealImgUrl).into(binding.toolbarImg)
        }

//        set the progressbar
        binding.linearProgressBar.visibility = View.VISIBLE
    }

    private fun setupCollapsingToolbar() {
        val appBarLayout = binding.appBarLayout
        val collapsingToolbarLayout = binding.collapsingToolbar

        appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val isCollapsed = abs(verticalOffset) - appBarLayout.totalScrollRange == 0

            // Hide title when expanded, show it when collapsed
            if (isCollapsed) {
                collapsingToolbarLayout.title = mealDetails?.strMeal
            } else {
                collapsingToolbarLayout.title = ""
            }
        }
    }
}