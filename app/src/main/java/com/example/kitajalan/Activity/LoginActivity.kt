package com.example.kitajalan.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kitajalan.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Mengambil ID dari Activity
        var username: EditText = findViewById(R.id.username_input)
        var password: EditText = findViewById(R.id.password_input)
        var login: Button = findViewById(R.id.btnLogin)
        var btnSignUp:TextView = findViewById(R.id.btnSignUp)


        //Mengambil Shared Preferences
        val sharedPrefs : SharedPreferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val usernameVal  = sharedPrefs.getString("username", null)
        val passwordVal = sharedPrefs.getString("password", null)

        val isLogin = sharedPrefs.getString("isLogin",null)

        if(isLogin == "1"){
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        login.setOnClickListener{
            val a = username.text.toString()
            val b = password.text.toString()

            if(a == usernameVal && b == passwordVal){
                val editor = sharedPrefs.edit()
                editor.putString("isLogin", "1")
                editor.apply()

                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Username atau password anda salah apakah ingin mengulang?")
                    .setPositiveButton("Yes"){ dialogInterface, which->
                        dialogInterface.dismiss()
                        Toast.makeText(this, "Oke", Toast.LENGTH_LONG).show()
                    }
                    .setNegativeButton("No"){ dialogInterface, which->
                        finish()
                    }
                    .show()
            }
        }

        //Membuat Aksi
        btnSignUp.setOnClickListener{
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }

        //Function Untuk Snackbar
    }
}