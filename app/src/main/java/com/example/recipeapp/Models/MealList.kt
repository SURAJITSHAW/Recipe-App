package com.example.recipeapp.Models

import java.io.Serializable

data class MealList(
    val meals: List<Meal>
): Serializable