package com.example.recipeapp.Fragments

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.recipeapp.Activities.MealDetailsActivity
import com.example.recipeapp.viewModels.HomeViewModel
import com.bumptech.glide.request.target.Target
import com.example.recipeapp.Activities.CategoryWiseRecipeActivity
import com.example.recipeapp.Models.Category
import com.example.recipeapp.Models.Meal
import com.example.recipeapp.adapter.CategoryAdapter
import com.example.recipeapp.adapter.TrendingMealsAdapter
import com.example.recipeapp.databinding.FragmentHomeBinding // Import the generated binding class

class HomeFragment : Fragment(), TrendingMealsAdapter.OnItemClickListener, CategoryAdapter.OnItemClickListener {

    companion object {
         const val MEAL_ID = "com.example.recipeapp.Fragments.randomRecipeId"
         const val IMG_URL = "com.example.recipeapp.Fragments.randomRecipeImageurl"
         const val CATEGORY_NAME = "com.example.recipeapp.Fragments.categoryId"
    }

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!! // Use binding reference to access views

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRandomRecipe()
        observeRandomRecipeLivedata()

        setUpTrendingNowRecyclerView()

        setUpCategoriesRecyclerView()
    }

    private fun setUpCategoriesRecyclerView() {
//        call the method in viewmodel to get the trending meals
        viewModel.getCategories()
//        observe the livedate
        viewModel.categoriesLiveData.observe(viewLifecycleOwner) { catList ->
            if (catList != null) {
                binding.categoryRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
                binding.categoryRecyclerView.adapter = CategoryAdapter(catList, this)
            }
        }
    }

    private fun setUpTrendingNowRecyclerView() {
//        call the method in viewmodel to get the trending meals
        viewModel.getTrendingMeals("chicken")
//        observe the livedate
        viewModel.trendingMealsLiveData.observe(viewLifecycleOwner) { mealsList ->
            if (mealsList != null) {
                binding.trendingRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.trendingRecyclerView.adapter = TrendingMealsAdapter(
                    mealsList,
                    this
                )
            }
        }
    }

    private fun observeRandomRecipeLivedata() {
        viewModel.randomRecipeLivedata.observe(viewLifecycleOwner) { recipe ->
            if (recipe != null) {
                Glide.with(requireContext())
                    .load(recipe.strMealThumb)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            Toast.makeText(requireContext(), "Failed to load image", Toast.LENGTH_SHORT).show()
                            showAnimation(false) // Hide animation on failure
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.randomText.visibility = View.VISIBLE
                            binding.randomText.text = recipe.strMeal
                            showAnimation(false)
                            return false
                        }
                    })
                    .into(binding.randomMealImageView)

                binding.randomMealImageView.setOnClickListener {
                    val intent = Intent(requireContext(), MealDetailsActivity::class.java)
                    intent.putExtra(MEAL_ID, recipe.idMeal)
                    intent.putExtra(IMG_URL, recipe.strMealThumb)
                    startActivity(intent)
                }
            } else {
                showAnimation(false)
            }
        }
    }

    private fun showAnimation(show: Boolean) {
        // Show or hide the Lottie animation and ImageView using ViewBinding
        if (show) {
            binding.lottieAnimationView.visibility = View.VISIBLE
            binding.randomMealImageView.visibility = View.GONE
        } else {
            binding.lottieAnimationView.visibility = View.GONE
            binding.randomMealImageView.visibility = View.VISIBLE
            binding.lottieAnimationView.cancelAnimation() // Stop the animation
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }

    override fun onItemClick(meal: Meal) {
        val intent = Intent(requireContext(), MealDetailsActivity::class.java)
        intent.putExtra(MEAL_ID, meal.idMeal)
        intent.putExtra(IMG_URL, meal.strMealThumb)
        startActivity(intent)

    }

    override fun onItemClick(category: Category) {
        val intent = Intent(requireContext(), CategoryWiseRecipeActivity::class.java)
        intent.putExtra(CATEGORY_NAME, category.strCategory)
        startActivity(intent)
    }
}
