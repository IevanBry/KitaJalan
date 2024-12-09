package com.example.kitajalan.fragment

import Domain.SettingsDomain
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import basic_list_view.SettingsAdapter
import com.example.kitajalan.activity.LoginActivity
import com.example.kitajalan.basic_recyclerview.RecyclerViewActivity
import com.example.kitajalan.basic_list_view.ListViews
import com.example.kitajalan.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val welcomeTextView: TextView = view.findViewById(R.id.textNameSettings)

        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val usernameVal = sharedPreferences.getString("username", "Guest")

        welcomeTextView.text = usernameVal.toString().replaceFirstChar { it.uppercase() }

        val settingsItems = listOf(
            SettingsDomain("Reset Welcome Screen", R.drawable.baseline_lock_reset_24),
            SettingsDomain("Melihat List", R.drawable.baseline_lock_reset_24),
            SettingsDomain("Contoh Recycler View", R.drawable.baseline_lock_reset_24),
            SettingsDomain("Help & Support", R.drawable.baseline_help_24),
            SettingsDomain("About Us", R.drawable.baseline_info_24),
            SettingsDomain("Logout", R.drawable.baseline_logout_24)
        )

        val listView: ListView = view.findViewById(R.id.settingsListView) // Make sure to add a ListView in fragment_settings.xml with this ID
        val adapter = SettingsAdapter(requireContext(), settingsItems)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            when (settingsItems[position].title) {
                "Logout" -> showLogoutDialog(sharedPreferences, view)
                "Reset Welcome Screen" -> resetWelcomeScreen(sharedPreferences, view)
                "Melihat List" -> startActivity(Intent(activity, ListViews::class.java))
                "Contoh Recycler View" -> startActivity(Intent(activity, RecyclerViewActivity::class.java))
                "Help & Support" -> replaceFragment(HelpSupportFragment())
                "About Us" -> replaceFragment(AboutUsFragment())
            }
        }
    }

    private fun showLogoutDialog(sharedPreferences: SharedPreferences, view: View) {
        AlertDialog.Builder(requireContext())
            .setTitle("Konfirmasi")
            .setMessage("Apakah Anda yakin ingin logout?")
            .setPositiveButton("Ya") { _, _ ->
//                val editor = sharedPreferences.edit()
//                editor.putString("isLogin", "0")
//                editor.apply()
                FirebaseAuth.getInstance().signOut()

                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                activity?.finish()

                ShowSnackBar(view, "Anda telah logout.")
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                Toast.makeText(requireContext(), "Tidak Logout", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
            .show()
    }

    private fun resetWelcomeScreen(sharedPreferences: SharedPreferences, view: View) {
        val editor = sharedPreferences.edit()
        editor.putString("isWelcome", "0")
        editor.apply()

        ShowSnackBar(view, "Welcome screen telah di-reset.")
    }

    private fun ShowSnackBar(view: View, message: String) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }
    }
}
