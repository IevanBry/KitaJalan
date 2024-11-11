package quiz_1

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import basic_list_view.SettingsAdapter
import com.example.kitajalan.Activity.basic_list_view.ListAdapter
import com.example.kitajalan.Activity.basic_list_view.ListModel
import com.example.kitajalan.Activity.basic_list_view.ListViews
import com.example.kitajalan.Activity.basic_recyclerview.RecyclerViewActivity
import com.example.kitajalan.Activity.fragment.AboutUsFragment
import com.example.kitajalan.Activity.fragment.HelpSupportFragment
import com.example.kitajalan.R

class Quiz1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val listView: ListView = findViewById(R.id.quizListView)
        val menuList = listOf(
            ListModel("Hitung Kubus", "Menu untuk menghitung volume kubus", R.drawable.baseline_menu_24),
            ListModel("Link Tutorial WebView", "ViewPager untuk menampilkan situs website tutorial online", R.drawable.baseline_home_24),
        )

        val adapter = ListAdapter(this, menuList)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            when (menuList[position].name) {
                "Hitung Kubus" -> startActivity(Intent(this, KubusActivity::class.java))
                "Link Tutorial WebView" -> startActivity(Intent(this, LinkTutorialActivity::class.java))
            }
        }
    }
}