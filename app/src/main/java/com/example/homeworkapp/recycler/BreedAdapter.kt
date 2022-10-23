package com.example.homeworkapp.recycler

import android.app.Activity
import android.media.ImageReader.OnImageAvailableListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkapp.R
import com.example.homeworkapp.databinding.ItemBreedsBinding
import com.google.android.material.card.MaterialCardView

class BreedAdapter(
    private val activity: Activity,
    private val list: List<Breed>,
    private val onItemClickListener: ((Int) -> Unit)? = null,
) : RecyclerView.Adapter<BreedAdapter.BreedHolder>() {

    private val flags = BooleanArray(list.size)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedHolder =
        BreedHolder(
            ItemBreedsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )



    override fun onBindViewHolder(holder: BreedHolder, position: Int) {
        with(holder) {
            name.text = list[position].name

            setCardBackgroundColor(root, position)

            root.setOnClickListener {
                flags[position] = true
                setCardBackgroundColor(root, position)
                onItemClickListener?.let { listener -> listener(position) }
            }
        }
    }

    override fun getItemCount(): Int = list.size

    private fun setCardBackgroundColor(card: MaterialCardView, position: Int) {
        card.setCardBackgroundColor(
            activity.resources.getColor(
                if (flags[position]) R.color.teal_200 else R.color.teal_700,
                activity.theme
            )
        )
    }

    inner class BreedHolder(binding: ItemBreedsBinding
    ): RecyclerView.ViewHolder(binding.root){
        val root = binding.root
        val name = binding.itemDogBreeds
    }

}