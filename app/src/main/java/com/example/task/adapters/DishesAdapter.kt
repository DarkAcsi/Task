package com.example.task.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task.databinding.ItemDishBinding
import com.example.task.requests.ItemDish

class DishesDiffCallback(
    private val oldList: List<ItemDish>,
    private val newList: List<ItemDish>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItemPosition == newItemPosition
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}

class DishesAdapter() :
    RecyclerView.Adapter<DishesAdapter.DishesViewHolder>() {
    var items: List<ItemDish> = emptyList()
        set(newValue) {
            val diffCallback = DishesDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDishBinding.inflate(inflater, parent, false)

        return DishesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DishesViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            Glide.with(holder.binding.root.context)
                .load(item.strMealThumb)
                .into(imageDish)
            nameDish.text = item.strMeal
            compositionDish.text = item.ingredients
            priceDish.text = "от ${item.idMeal % 1000}р"
        }
    }

    class DishesViewHolder(
        val binding: ItemDishBinding
    ) : RecyclerView.ViewHolder(binding.root)

}
