package com.example.recipeapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.Models.Meal
import com.example.recipeapp.repository.HomeRepo

class HomeViewModel: ViewModel() {
    private val repo = HomeRepo()

//    exposing livedate to the ui for random meal
    val randomRecipeLivedata: LiveData<Meal?> = repo.recipe

    fun getRandomRecipe() {
        repo.getRandomRecipe()
    }

    //    exposing livedate to the ui for trending meals
    val trendingMealsLiveData: LiveData<List<Meal>?> = repo.recipesByCategory

    fun getTrendingMeals(category: String) {
        repo.getRecipesByCat(category)
    }

}