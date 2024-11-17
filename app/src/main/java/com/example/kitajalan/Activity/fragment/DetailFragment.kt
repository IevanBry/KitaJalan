package com.example.kitajalan.Activity.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.kitajalan.R

class DetailFragment : Fragment() {

    private lateinit var descriptionTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var detailImageView: ImageView
    private lateinit var btnBack: ImageView
    private lateinit var favoriteButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        descriptionTextView = view.findViewById(R.id.descriptionText)
        priceTextView = view.findViewById(R.id.priceText)
        detailImageView = view.findViewById(R.id.detailImg)
        btnBack = view.findViewById(R.id.iconBack)
        favoriteButton = view.findViewById(R.id.favorite_button)

        val description = arguments?.getString("description")
        val price = arguments?.getString("price")
        val imageResId = arguments?.getInt("image")
        val isFavorite = arguments?.getBoolean("isFavorite") ?: false


        descriptionTextView.text = description
        priceTextView.text = "Rp. $price"
        imageResId?.let { detailImageView.setImageResource(it) }

        btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        favoriteButton.setOnClickListener{
            updateFavoriteIcon(isFavorite)
        }

        return view
    }

    private fun updateFavoriteIcon(isFavorite : Boolean) {
        favoriteButton.setImageResource(
            if (isFavorite)
                R.drawable.fav
            else
                R.drawable.baseline_favorite_24
        )
    }
}
