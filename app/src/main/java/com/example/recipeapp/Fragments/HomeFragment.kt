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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.recipeapp.Activities.MealDetailsActivity
import com.example.recipeapp.viewModels.HomeViewModel
import com.bumptech.glide.request.target.Target
import com.example.recipeapp.databinding.FragmentHomeBinding // Import the generated binding class

class HomeFragment : Fragment() {

    companion object {
         const val MEAL_ID = "com.example.recipeapp.Fragments.randomRecipeId"
         const val IMG_URL = "com.example.recipeapp.Fragments.randomRecipeImageurl"
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
        // Inflate the layout using ViewBinding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root // Return the root view of the binding object
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ask ViewModel to get the data from repo
        viewModel.getRandomRecipe()

        // Observe the LiveData from the ViewModel
        observeRandomRecipeLivedata()
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
                    intent.putExtra(HomeFragment.MEAL_ID, recipe.idMeal)
                    intent.putExtra(HomeFragment.IMG_URL, recipe.strMealThumb)
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
}
