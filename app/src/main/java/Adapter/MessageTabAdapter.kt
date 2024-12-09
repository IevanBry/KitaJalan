package Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kitajalan.fragment.InformationFragment
import com.example.kitajalan.fragment.NotificationFragment

class MessageTabAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val fragmentList = listOf(
        NotificationFragment(),
        InformationFragment()
    )

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}