package com.example.recipeapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.Models.Meal
import com.example.recipeapp.db.MealDB
import com.example.recipeapp.repository.HomeRepo
import com.example.recipeapp.repository.MealDetailsRepo
import kotlinx.coroutines.launch

class MealDetailsViewModel(application: Application): AndroidViewModel(application) {

    private val repository: MealDetailsRepo

    val allRecipes: LiveData<List<Meal>>

    init {
        val recipeDao = MealDB.getDatabase(application).mealDao()
        repository = MealDetailsRepo(recipeDao)
        allRecipes = repository.allRecipes

    }

    //    exposing livedate to the ui
    val mealDetailsLivedata: LiveData<Meal?> = repository.mealDetailsLivedata

    fun getMealDetails(id: String) {
        repository.getMeal(id)
    }

    // room operations

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            repository.insert(meal)
        }
    }

    fun delete(recipe: Meal) = viewModelScope.launch {
        repository.delete(recipe)
    }


}