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
import basic_list_view.ListView
import com.example.kitajalan.Activity.LoginActivity
import com.example.kitajalan.Activity.basic_recyclerview.RecyclerViewActivity
import com.example.kitajalan.R
import com.google.android.material.snackbar.Snackbar

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
        val btnResetWelcome : RelativeLayout = view.findViewById(R.id.btnResetWelcome)
        val btnList : RelativeLayout = view.findViewById(R.id.btnList)
        val btnRecycler : RelativeLayout = view.findViewById(R.id.btnRecyclerView)
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

                    // Tampilkan Snackbar saat logout
                    ShowSnackBar(view, "Anda telah logout.")
                }
                .setNegativeButton("Tidak") { dialogInterface, which ->
                    Toast.makeText(requireContext(), "Tidak Logout", Toast.LENGTH_LONG).show()
                    dialogInterface.dismiss()
                }
                .show()
        }
        btnResetWelcome.setOnClickListener{
            val editor = sharedPreferences.edit()
            editor.putString("isWelcome", "0")
            editor.apply()

            ShowSnackBar(view, "Welcome screen telah di-reset.")
        }

        btnList.setOnClickListener {
            val intent = Intent(activity, ListView::class.java)
            startActivity(intent)
        }

        btnRecycler.setOnClickListener {
            val intent = Intent(activity, RecyclerViewActivity::class.java)
            startActivity(intent)
        }
    }
    private fun ShowSnackBar(view: View, message: String) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        snackbar.show()
    }
}
