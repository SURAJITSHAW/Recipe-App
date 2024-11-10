package com.example.recipeapp.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.Activities.MealDetailsActivity
import com.example.recipeapp.Fragments.HomeFragment.Companion.IMG_URL
import com.example.recipeapp.Fragments.HomeFragment.Companion.MEAL_ID
import com.example.recipeapp.Models.Meal
import com.example.recipeapp.R
import com.example.recipeapp.adapter.RecipeAdapter
import com.example.recipeapp.databinding.FragmentFavBinding
import com.example.recipeapp.viewModels.MealDetailsViewModel


class FavFragment : Fragment(), RecipeAdapter.OnItemClickListener {

    private lateinit var  viewModel: MealDetailsViewModel

    // Declare binding variable
    private var _binding: FragmentFavBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using binding
        _binding = FragmentFavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the ViewModel
        viewModel = ViewModelProvider(this)[MealDetailsViewModel::class.java]

        viewModel.allRecipes.observe(viewLifecycleOwner){ mealList ->
            if (mealList != null){

                binding.tvFavRecipesCountText.text = "Total Favourites: " + mealList.size.toString()
                binding.rvFavRecipeRecyler.apply {
                    adapter = RecipeAdapter(mealList, this@FavFragment)
                    layoutManager = GridLayoutManager(context, 1, LinearLayoutManager.VERTICAL, false)

                }
            } else {
                Toast.makeText(requireContext(), "No favourite meals", Toast.LENGTH_SHORT).show()

                Log.d("FavFragment", "mealList is null or empty")
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear the binding reference when the view is destroyed
        _binding = null
    }

    override fun onItemClick(meal: Meal) {
        val intent = Intent(requireContext(), MealDetailsActivity::class.java)
        intent.putExtra(HomeFragment.MEAL_ID, meal.idMeal)
        intent.putExtra(HomeFragment.IMG_URL, meal.strMealThumb)
        startActivity(intent)
    }
}