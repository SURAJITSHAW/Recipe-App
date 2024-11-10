package com.example.recipeapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipeapp.Models.Meal

@Database(entities = [Meal::class], version = 1, exportSchema = false)
abstract class MealDB: RoomDatabase() {
    abstract fun mealDao(): FavMealsDao

    companion object {
        @Volatile
        private var INSTANCE: MealDB? = null

        fun getDatabase(context: Context): MealDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealDB::class.java,
                    "recipe_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}