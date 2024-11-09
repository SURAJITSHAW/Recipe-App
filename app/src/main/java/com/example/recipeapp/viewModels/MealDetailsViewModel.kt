package com.example.recipeapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.Models.Meal
import com.example.recipeapp.repository.HomeRepo
import com.example.recipeapp.repository.MealDetailsRepo

class MealDetailsViewModel: ViewModel() {

    private val repo = MealDetailsRepo()

    //    exposing livedate to the ui
    val mealDetailsLivedata: LiveData<Meal?> = repo.mealDetailsLivedata

    fun getMealDetails(id: String) {
        repo.getMeal(id)
    }
}