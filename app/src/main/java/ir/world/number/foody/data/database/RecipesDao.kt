package ir.world.number.foody.data.database

import androidx.room.*
import ir.world.number.foody.data.database.entities.FavoritesEntity
import ir.world.number.foody.data.database.entities.FoodJokeEntity
import ir.world.number.foody.data.database.entities.RecipesEntity
import ir.world.number.foody.models.FoodJoke
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertRecipes(recipesEntity: RecipesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity)

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
     fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipesEntity>>

    @Query("SELECT *FROM favorite_recipes_table ORDER BY id ASC")
    fun readFavoriteRecipes(): Flow<List<FavoritesEntity>>


    @Query("SELECT * FROM food_joke_table ORDER BY id ASC")
    fun readFoodJoke(): Flow<List<FoodJokeEntity>>


    @Delete
    fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity)

    @Query("DELETE FROM favorite_recipes_table")
    fun deleteAllFavoriteRecipes()
}