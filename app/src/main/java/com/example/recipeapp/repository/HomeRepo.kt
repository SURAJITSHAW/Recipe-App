package com.example.recipeapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.Models.Meal
import com.example.recipeapp.Models.MealList
import com.example.recipeapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepo {
    private var _recipe = MutableLiveData<Meal?>()
    val recipe: LiveData<Meal?> get() = _recipe

    private var _recipesByCategory = MutableLiveData<List<Meal>?>()
    val recipesByCategory: LiveData<List<Meal>?> get() = _recipesByCategory


    fun getRecipesByCat(category: String) {
        RetrofitInstance.api.getMealByCategory(category).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful) {
                    // Handle the response and update LiveData
                    val mealList = response.body()?.meals
                    _recipesByCategory.value = mealList
                } else {
                    _recipesByCategory.value = null // Handle failure scenario
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                // Handle error, maybe show a toast or log
                _recipesByCategory.value = null // If there's a failure, you can set an empty list
            }
        })
    }

    fun getRandomRecipe(){

        RetrofitInstance.api.getRandromRecipe().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful) {
                    val recipe = response.body()?.meals?.get(0)
                    _recipe.value = recipe
                } else {
                    _recipe.value = null
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                _recipe.value = null
            }
        })
    }
}