package com.example.task.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.task.databinding.ItemCategoryBinding
import com.example.task.requests.Category
import androidx.core.content.ContextCompat
import com.example.task.R

class CategoriesDiffCallback(
    private val oldList: List<Category>,
    private val newList: List<Category>
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

interface CategoryListener {

    fun changeCategory(category: Category)

}

class CategoriesAdapter(val context: Context, private val categoryListener: CategoryListener) :
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private var selectedPosition = 0
    private var lastPosition = 0

    var items: List<Category> = emptyList()
        set(newValue) {
            val diffCallback = CategoriesDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)

        binding.nameCategory.setOnClickListener {
            changeCategory(binding.nameCategory.tag as List<Any>)
        }

        return CategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            nameCategory.tag = listOf(item, position)
            nameCategory.text = item.strCategory
            if (position == selectedPosition) {
                nameCategory.setTextColor(ContextCompat.getColor(context, R.color.main_color))
                nameCategory.setBackgroundResource(R.drawable.shape_radius6_main)
            } else {
                nameCategory.setTextColor(ContextCompat.getColor(context, R.color.back_color_text))
                nameCategory.setBackgroundResource(R.drawable.shape_radius6_back)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun changeCategory(selectedItem: List<Any>){
        val item = selectedItem[0] as Category
        selectedPosition = selectedItem[1] as Int
        if (selectedPosition != lastPosition){
            lastPosition = selectedPosition
            categoryListener.changeCategory(item)
            notifyDataSetChanged()
        }
    }

    class CategoriesViewHolder(
        val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root)

}
