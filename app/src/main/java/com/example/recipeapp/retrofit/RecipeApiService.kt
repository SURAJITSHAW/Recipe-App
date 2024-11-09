package com.example.recipeapp.retrofit

import com.example.recipeapp.Models.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {

    @GET("random.php")
    fun getRandromRecipe(): Call<MealList>

    @GET("lookup.php")
    fun getRecipe(@Query("i") id: String): Call<MealList>
}