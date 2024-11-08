package com.example.recipeapp.retrofit

import com.example.recipeapp.Models.Meal
import retrofit2.Call
import retrofit2.http.GET

interface RecipeApiService {

    @GET("random.php")
    fun getRandromRecipe(): Call<Meal>
}