package com.example.recipeapp.Fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.recipeapp.Models.Meal
import com.example.recipeapp.R
import com.example.recipeapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.bumptech.glide.request.target.Target


class HomeFragment : Fragment() {

    private lateinit var imgView: ImageView
    private lateinit var loadingAnimation: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize ImageView and LottieAnimationView
        imgView = view.findViewById(R.id.randomMealImageView)
        loadingAnimation = view.findViewById(R.id.lottieAnimationView)

        // Start by hiding ImageView and showing Lottie animation
//        imgView.visibility = View.GONE
//        showAnimation(true)

        // Fetch the random meal
        fetchRandomMeal()

        return view
    }

    private fun showAnimation(show: Boolean) {
        // Show or hide the Lottie animation and ImageView
        if (show) {
            loadingAnimation.visibility = View.VISIBLE
            imgView.visibility = View.GONE
        } else {
            loadingAnimation.visibility = View.GONE
            imgView.visibility = View.VISIBLE
            loadingAnimation.cancelAnimation() // stop the animation
        }
    }

    private fun fetchRandomMeal() {
        val call = RetrofitInstance.api.getRandromRecipe()

        call.enqueue(object : Callback<Meal> {
            override fun onResponse(call: Call<Meal>, response: Response<Meal>) {
                if (response.isSuccessful) {
                    val randomMeal = response.body()
                    if (randomMeal != null && randomMeal.meals.isNotEmpty()) {
                        Glide.with(requireContext())
                            .load(randomMeal.meals[0].strMealThumb)
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
                                    showAnimation(false) // Hide animation on success
                                    return false
                                }
                            })
                            .into(imgView)

                        Toast.makeText(requireContext(), "Random meal: ${randomMeal.meals[0].strMeal}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Error: Unable to fetch meal", Toast.LENGTH_SHORT).show()
                    showAnimation(false) // Hide animation if there's an error
                }
            }

            override fun onFailure(call: Call<Meal>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed to fetch data: ${t.message}", Toast.LENGTH_SHORT).show()
                showAnimation(false) // Hide animation if the API call fails
            }
        })
    }
}
