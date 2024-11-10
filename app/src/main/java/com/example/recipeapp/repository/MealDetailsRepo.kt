package com.example.recipeapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.Models.Meal
import com.example.recipeapp.Models.MealList
import com.example.recipeapp.db.FavMealsDao
import com.example.recipeapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDetailsRepo (private val recipeDao: FavMealsDao){
    private var _mealDetails = MutableLiveData<Meal?>()
    val mealDetailsLivedata: LiveData<Meal?> get() = _mealDetails

    fun getMeal(id : String){

        RetrofitInstance.api.getRecipe(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful) {
                    val recipe = response.body()?.meals?.get(0)
                    _mealDetails.value = recipe
                } else {
                    _mealDetails.value = null
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                _mealDetails.value = null
            }
        })
    }

    //    Room DB operations

    suspend fun insert(recipe: Meal) {
        recipeDao.insert(recipe)
    }
}