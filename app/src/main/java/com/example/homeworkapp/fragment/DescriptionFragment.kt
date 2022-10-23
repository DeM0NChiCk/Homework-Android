package com.example.homeworkapp.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.homeworkapp.databinding.FragmentDescriptionBinding
import com.example.homeworkapp.recycler.BreedRepository

class DescriptionFragment: Fragment() {
    private lateinit var binding: FragmentDescriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDescriptionBinding.inflate(inflater,container,false)

        val position = arguments?.getInt("itemPosition", 0)!!
        val breed = BreedRepository().getBreed()[position]

        with(binding) {
            tvDogBreedName.text = breed.name
            tvDogBreedInformation.text = breed.text_description

            Glide.with(requireContext())
                .load(breed.image_url)
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        dogImageLoader.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        dogImageLoader.visibility = View.GONE
                        return false
                    }
                })
                .into(breedImage)

            return root
        }
    }

    companion object {
        fun getInstance(itemPosition: Int): DescriptionFragment {
            val descriptionFragment = DescriptionFragment()

            descriptionFragment.arguments =
                bundleOf("itemPosition" to itemPosition)

            return descriptionFragment
        }
    }
}