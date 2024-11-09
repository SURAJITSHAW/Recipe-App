package com.example.recipeapp.Activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.Fragments.HomeFragment
import com.example.recipeapp.Fragments.HomeFragment.Companion.IMG_URL
import com.example.recipeapp.Fragments.HomeFragment.Companion.MEAL_ID
import com.example.recipeapp.Models.Meal
import com.example.recipeapp.R
import com.example.recipeapp.adapter.RecipeAdapter
import com.example.recipeapp.databinding.ActivityCategoryBinding
import com.example.recipeapp.viewModels.HomeViewModel

class CategoryWiseRecipeActivity : AppCompatActivity(), RecipeAdapter.OnItemClickListener {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var categoryName: String
    private var recipesCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        get intent data
        getIntentData()
//        get the data and observe it
        setUpApiCall()
//        set up recycler view
        setUpRecyclerView(mealList = emptyList())
    }

    private fun setUpRecyclerView(mealList: List<Meal>) {
        // Initialize the adapter
        recipeAdapter = RecipeAdapter(mealList, this)

        // Set up the RecyclerView
        binding.rvCatWiseRecipe.apply {
            adapter = recipeAdapter
            layoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setUpApiCall() {
        viewModel.getTrendingMeals(categoryName)

        viewModel.trendingMealsLiveData.observe(this) { mealList ->
            if (mealList != null) {
                recipesCount = mealList.size
                binding.tvRecipesCountText.text = "$categoryName: $recipesCount"

                setUpRecyclerView(mealList)
            }
        }
    }

    private fun getIntentData() {
        categoryName = intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!
        binding.tvRecipesCountText.text = categoryName
    }

    override fun onItemClick(meal: Meal) {
        val intent = Intent(this, MealDetailsActivity::class.java)
        intent.putExtra(MEAL_ID, meal.idMeal)
        intent.putExtra(IMG_URL, meal.strMealThumb)
        startActivity(intent)
    }
}