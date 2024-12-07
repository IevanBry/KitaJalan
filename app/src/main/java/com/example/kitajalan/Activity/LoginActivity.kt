package com.example.kitajalan.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kitajalan.R
import com.example.kitajalan.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPrefs: SharedPreferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val usernameVal = sharedPrefs.getString("username", null)
        val passwordVal = sharedPrefs.getString("password", null)
        val isLogin = sharedPrefs.getString("isLogin", null)
        val userRole = sharedPrefs.getString("role", "user")

        // Cek jika user sudah login
        if (isLogin == "1") {
            if (userRole == "admin") {
                val i = Intent(this, AdminActivity::class.java)
                startActivity(i)
            } else {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            }
            finish()
        }

        binding.btnLogin.setOnClickListener {
            val a = binding.usernameInput.text.toString()
            val b = binding.passwordInput.text.toString()

            if (a == usernameVal && b == passwordVal) {
                val editor = sharedPrefs.edit()
                editor.putString("isLogin", "1")

                if (a == "admin") {
                    editor.putString("role", "admin")
                } else {
                    editor.putString("role", "user")
                }
                editor.apply()

                if (a == "admin") {
                    val i = Intent(this, AdminActivity::class.java)
                    startActivity(i)
                } else {
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                }
                finish()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Username atau password anda salah apakah ingin mengulang?")
                    .setPositiveButton("Yes") { dialogInterface, _ ->
                        dialogInterface.dismiss()
                        Toast.makeText(this, "Oke", Toast.LENGTH_LONG).show()
                    }
                    .setNegativeButton("No") { dialogInterface, _ ->
                        finish()
                    }
                    .show()
            }
        }

        binding.btnSignUp.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.btnForgot.setOnClickListener {
            val i = Intent(this, ForgotActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
