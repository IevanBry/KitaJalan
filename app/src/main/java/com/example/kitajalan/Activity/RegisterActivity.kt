package com.example.kitajalan.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kitajalan.R
import com.example.kitajalan.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPrefs: SharedPreferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)

        binding.btnRegister.setOnClickListener {
            val username = binding.usernameInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val confirmPassword = binding.confirmpasswordInput.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    val editor = sharedPrefs.edit()
                    editor.putString("username", username)
                    editor.putString("password", password)
                    editor.apply()

                    val i = Intent(this, LoginActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    ShowSnackBar("Password dan confirm password tidak sama")
                }
            } else {
                ShowSnackBar("Tolong isi semua kolom")
            }
        }

        binding.btnSignIn.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun ShowSnackBar(message: String) {
        val view = this.findViewById<View>(android.R.id.content)
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
