package com.example.kitajalan.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kitajalan.R
import com.example.kitajalan.basic_api.data.firebase.FirebaseAuthService
import com.example.kitajalan.basic_api.data.repository.FirebaseRepository
import com.example.kitajalan.basic_api.ui.viewModel.FirebaseViewModel
import com.example.kitajalan.basic_api.ui.viewModel.Resource
import com.example.kitajalan.basic_api.utils.ViewModelFactory
import com.example.kitajalan.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private val firebaseViewModel: FirebaseViewModel by viewModels {
        ViewModelFactory(FirebaseViewModel::class.java) {
            val repository = FirebaseRepository(FirebaseAuthService())
            FirebaseViewModel(repository)
        }
    }

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
//        val isLogin = sharedPrefs.getString("isLogin", null)
        val isLoggedIn = FirebaseAuth.getInstance().currentUser != null
        val userRole = sharedPrefs.getString("role", "user")

        // Cek jika user sudah login
        if (isLoggedIn) {
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
            val username = binding.usernameInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username dan Password tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            firebaseViewModel.login(username, password)
//            val a = binding.usernameInput.text.toString()
//            val b = binding.passwordInput.text.toString()
//
//            if (a == usernameVal && b == passwordVal) {
//                val editor = sharedPrefs.edit()
//                editor.putString("isLogin", "1")
//
//                if (a == "admin") {
//                    editor.putString("role", "admin")
//                } else {
//                    editor.putString("role", "user")
//                }
//                editor.apply()
//
//                if (a == "admin") {
//                    val i = Intent(this, AdminActivity::class.java)
//                    startActivity(i)
//                } else {
//                    val i = Intent(this, MainActivity::class.java)
//                    startActivity(i)
//                }
//                finish()
//            } else {
//                AlertDialog.Builder(this)
//                    .setTitle("Error")
//                    .setMessage("Username atau password anda salah apakah ingin mengulang?")
//                    .setPositiveButton("Yes") { dialogInterface, _ ->
//                        dialogInterface.dismiss()
//                        Toast.makeText(this, "Oke", Toast.LENGTH_LONG).show()
//                    }
//                    .setNegativeButton("No") { dialogInterface, _ ->
//                        finish()
//                    }
//                    .show()
//            }
        }

        firebaseViewModel.loginState.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Log.d("Firebase User Authentication","Mengirim Username Password...")
                }
                is Resource.Success -> {
                    Log.d("Firebase User Authetication","Halo ${firebaseViewModel.getCurrentUser().toString()}")
                    val user = resource.data
                    val editor = sharedPrefs.edit()

                    editor.putString("username", user?.username)
                    editor.apply()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is Resource.Error -> {
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
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
