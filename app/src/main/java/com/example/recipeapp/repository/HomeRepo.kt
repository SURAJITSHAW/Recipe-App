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