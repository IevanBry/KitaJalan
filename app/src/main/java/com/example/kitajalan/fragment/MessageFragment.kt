package com.example.kitajalan.fragment

import Adapter.MessageTabAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.kitajalan.R
import com.example.kitajalan.databinding.FragmentMessageBinding
import com.google.android.material.tabs.TabLayoutMediator

class MessageFragment : Fragment() {
    private var _binding : FragmentMessageBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_message, container, false)
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
//
//        val tabLayout: TabLayout = view.findViewById(R.id.tabLayout)
//        val viewPager: ViewPager2 = view.findViewById(R.id.viewPager)

        binding.viewPager.adapter = MessageTabAdapter(requireActivity())

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Notification"
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_menu_24)
                }

                1 -> {
                    tab.text = "Information"
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_home_24)
                }
                2 -> {
                    tab.text = "Information1"
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_favorite_24)
                }
                3 -> {
                    tab.text = "Information 2"
                }
                4 -> {
                    tab.text = "Information 3"
                }
            }
        }.attach()
        return binding.root;
    }
}