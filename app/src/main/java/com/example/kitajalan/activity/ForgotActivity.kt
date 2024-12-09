package com.example.kitajalan.activity

import android.annotation.SuppressLint
import android.content.Intent
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

class ForgotActivity : AppCompatActivity() {

    private lateinit var email : EditText
    private lateinit var submit : Button
    private lateinit var informasi : TextView
    private lateinit var back : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        email = findViewById(R.id.textEditEmail)
        submit = findViewById(R.id.btnSubmitForgot)
        informasi = findViewById(R.id.textInfoForgot)
        back = findViewById(R.id.btnKembali)

        informasi.visibility =  View.GONE
        back.visibility = View.GONE

        submit.setOnClickListener{
            val textEmail = email.text.toString()
            if(textEmail.isEmpty()){
                informasi.visibility = View.VISIBLE
                informasi.text = "Tolong inputkan email kamu"
            } else if(textEmail == "2255301075@mahasiswa.pcr.ac.id") {
                email.visibility = View.GONE
                submit.visibility = View.GONE
                informasi.visibility = View.VISIBLE
                back.visibility = View.VISIBLE
                informasi.text = "Link reset password sudah dikirim ke $textEmail .Silahkan akses dan ikuti langkah yang tersedia"
            } else {
                informasi.visibility = View.VISIBLE
                informasi.text = "Email yang dimasukkan tidak valid"
            }
        }

        back.setOnClickListener{
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}