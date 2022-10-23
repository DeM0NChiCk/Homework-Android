package com.example.homeworkapp.recycler.holder

import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.homeworkapp.databinding.ItemCategoryBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkapp.recycler.data.models.Category

class CategoryHolder(binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root){
    private val root = binding.root
    private val header = binding.itemCategoryHeader
    private val icon = binding.itemCategoryIcon
    private val name = binding.itemNameCategory

    fun bindItem(category: Category, localPosition: Int){
        if (localPosition == 0)
            header.visibility = View.VISIBLE
        else
            header.visibility = View.GONE

        icon.setImageDrawable(
            ResourcesCompat.getDrawable(
                root.resources,
                category.icon,
                root.context.theme
            )
        )
        name.text = category.name
    }
}


