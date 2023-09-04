package ir.world.number.foody.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.world.number.foody.models.Result
import ir.world.number.foody.util.Constans.Companion.FAVORITE_RECIPES_TABLE

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        var result: Result
)
