package com.example.kitajalan.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kitajalan.R
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Mengambil ID dari Activity
        var btnSignIn: TextView = findViewById(R.id.btnSignIn)
        var btnRegister: Button = findViewById(R.id.btnRegister)
        var username: EditText = findViewById(R.id.username_input)
        var password: EditText = findViewById(R.id.password_input)
        var confirm_password: EditText = findViewById(R.id.confirmpassword_input)

        //Membuat Shared Preferences
        val sharedPrefs : SharedPreferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)

        btnRegister.setOnClickListener{
            val a = username.text.toString()
            val b = password.text.toString()
            val c = confirm_password.text.toString()

            if(a.isNotEmpty() && b.isNotEmpty() && c.isNotEmpty()){
                if(b == c){
                    val editor =  sharedPrefs.edit()
                    editor.putString("username", a)
                    editor.putString("password", b)
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

        //Membuat Aksi
        btnSignIn.setOnClickListener{
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }
    private fun ShowSnackBar(message: String){
        val view = this.findViewById<View>(android.R.id.content)
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)

        snackbar.show()
    }
}