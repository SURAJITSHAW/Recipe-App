package com.example.recipeapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.Models.Meal

@Dao
interface FavMealsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Meal)

    @Query("SELECT * FROM mealInfo ORDER BY strMeal ASC")
    fun getAllRecipes(): LiveData<List<Meal>>

    @Delete
    suspend fun delete(recipe: Meal)
}