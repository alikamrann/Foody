package ir.world.number.foody.bindingAdapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.datastore.preferences.protobuf.Internal
import androidx.recyclerview.widget.RecyclerView
import ir.world.number.foody.adapter.FavoriteRecipesAdapter
import ir.world.number.foody.data.database.entities.FavoritesEntity

class FavoriteRecipesBinding {

    companion object {


        @BindingAdapter("setVisibility","setData", requireAll = false)
        @JvmStatic
        fun setVisibility(view :View ,favoritesEntity: List<FavoritesEntity>?, mapAdapter: FavoriteRecipesAdapter?){
            when(view){
                is RecyclerView -> {
                    val dataCheck =favoritesEntity.isNullOrEmpty()
                    view.isInvisible =dataCheck
                    if(!dataCheck){
                        favoritesEntity?.let { mapAdapter?.setData(it) }
                    }
                }
                else-> view.isVisible = favoritesEntity.isNullOrEmpty()
            }
        }
    }
}