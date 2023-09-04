package ir.world.number.foody.data

import ir.world.number.foody.data.database.RecipesDao
import ir.world.number.foody.data.database.entities.FavoritesEntity
import ir.world.number.foody.data.database.entities.FoodJokeEntity
import ir.world.number.foody.data.database.entities.RecipesEntity
import ir.world.number.foody.models.FoodJoke
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {
     fun readDatabase():Flow<List<RecipesEntity>>
    {
        return recipesDao.readRecipes()
    }


    fun  readFavoriteRecipes(): Flow<List<FavoritesEntity>>{
        return recipesDao.readFavoriteRecipes()
    }

    fun readFoodJoke(): Flow<List<FoodJokeEntity>>{
        return recipesDao.readFoodJoke()
    }


    suspend fun insertReciprs(recipesEntity: RecipesEntity){
        recipesDao.insertRecipes(recipesEntity)
    }

    suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity){
        recipesDao.insertFavoriteRecipe(favoritesEntity)
    }

    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity){
        recipesDao.insertFoodJoke(foodJokeEntity)
    }

    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity){
        recipesDao.deleteFavoriteRecipe(favoritesEntity)
    }

    suspend fun  deleteAllFavoriteRecipes(){
        recipesDao.deleteAllFavoriteRecipes()
    }
}