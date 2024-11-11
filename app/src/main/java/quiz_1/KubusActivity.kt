package quiz_1

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

class KubusActivity : AppCompatActivity() {
    private lateinit var sisi : EditText
    private lateinit var submit : Button
    private lateinit var informasi : TextView
    private lateinit var kembali : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kubus)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sisi = findViewById(R.id.InputSisi)
        submit = findViewById(R.id.btnSubmitSisi)
        informasi = findViewById(R.id.textInfoVolume)
        kembali = findViewById(R.id.btnKembali)

        informasi.visibility = View.GONE

        submit.setOnClickListener{
            val sisi = sisi.text.toString()
            if(sisi.isEmpty()){
                informasi.visibility = View.VISIBLE
                informasi.text = "Tolong inputkan sesuatu"
            } else {
                val volume = sisi.toInt() * sisi.toInt() * sisi.toInt()
                informasi.visibility = View.VISIBLE
                informasi.text = "Volume kubus adalah $volume"
            }
        }

        kembali.setOnClickListener{
            val i = Intent(this, Quiz1Activity::class.java)
            startActivity(i)
            finish()
        }
    }
}