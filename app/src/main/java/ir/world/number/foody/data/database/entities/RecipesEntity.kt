package ir.world.number.foody.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.world.number.foody.models.FoodRecipe
import ir.world.number.foody.util.Constans
import ir.world.number.foody.util.Constans.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0;
}