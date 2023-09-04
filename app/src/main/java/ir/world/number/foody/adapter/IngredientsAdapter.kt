package ir.world.number.foody.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ir.world.number.foody.R
import ir.world.number.foody.databinding.IngredientsRowLayoutBinding
import ir.world.number.foody.models.ExtendedIngredient
import ir.world.number.foody.util.Constans.Companion.BASE_IMAGE_URL
import ir.world.number.foody.util.RecipesDiffUtil
import java.util.*

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {
    class MyViewHolder(val binding : IngredientsRowLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    private var ingredientList = emptyList<ExtendedIngredient>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(IngredientsRowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.ingredientImageView.load(BASE_IMAGE_URL + ingredientList[position].image)
        holder.binding.ingredientName.text = ingredientList[position].name.capitalize(Locale.ROOT)
        holder.binding.ingredientUnit.text = ingredientList[position].unit
        holder.binding.ingredientAmount.text = ingredientList[position].amount.toString()
        holder.binding.ingredientConsistency.text = ingredientList[position].consistency
        holder.binding.ingredientOriginal.text = ingredientList[position].original
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil =
                RecipesDiffUtil(ingredientList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}