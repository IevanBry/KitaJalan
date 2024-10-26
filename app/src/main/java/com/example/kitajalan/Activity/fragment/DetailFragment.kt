package com.example.kitajalan.Activity.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kitajalan.R

class DetailFragment : Fragment() {

    private lateinit var descriptionTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        // Inisialisasi TextView
        descriptionTextView = view.findViewById(R.id.descriptionText)

        // Mengambil data dari Bundle
        val description = arguments?.getString("description")
        descriptionTextView.text = description

        return view
    }
}