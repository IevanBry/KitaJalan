package com.example.kitajalan.Activity.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kitajalan.R
import com.example.kitajalan.databinding.FragmentInformationBinding
import com.example.kitajalan.databinding.FragmentNotificationBinding

class NotificationFragment : Fragment() {
    private var _binding : FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)

        return binding.root
//        return inflater.inflate(R.layout.fragment_notification, container, false)
    }
}