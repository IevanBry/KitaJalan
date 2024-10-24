package com.example.kitajalan.Activity.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.kitajalan.Activity.LoginActivity
import com.example.kitajalan.R

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnLogout: RelativeLayout = view.findViewById(R.id.btnLogout)
        val welcomeTextView : TextView = view.findViewById(R.id.textNameSettings)

        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val usernameVal = sharedPreferences.getString("username", "Guest")

        welcomeTextView.text = usernameVal.toString().replaceFirstChar { it.uppercase() }

        btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Konfirmasi")
                .setMessage("Apakah Anda yakin ingin logout?")
                .setPositiveButton("Ya") { dialogInterface, which ->
                    val editor = sharedPreferences.edit()
//                    editor.clear()
                    editor.putString("isLogin", "0")
                    editor.apply()

                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    activity?.finish()
                }
                .setNegativeButton("Tidak") { dialogInterface, which ->
                    Toast.makeText(requireContext(), "Tidak Logout", Toast.LENGTH_LONG).show()
                    dialogInterface.dismiss()
                }
                .show()
        }
    }
}
