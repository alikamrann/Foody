package ir.world.number.foody.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.world.number.foody.models.FoodJoke
import ir.world.number.foody.util.Constans.Companion.FOOD_JOKE_TABLE


@Entity(tableName = FOOD_JOKE_TABLE)
class FoodJokeEntity(
        @Embedded
        var foodJoke : FoodJoke
) {
    @PrimaryKey(autoGenerate = false)
    var id:Int  = 0
}