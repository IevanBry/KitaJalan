package basic_list_view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kitajalan.R
import android.widget.ListView
import android.widget.Toast

class ListView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val listView: ListView = findViewById(R.id.listView)
        val menuList = listOf(
            ListModel("Menu 1", "Deskripsi menu 1", R.drawable.baseline_menu_24),
            ListModel("Menu 2", "Deskripsi menu 2", R.drawable.baseline_home_24),
            ListModel("Menu 4", "Deskripsi menu 4", R.drawable.baseline_person_24),
            ListModel("Menu 5", "Deskripsi menu 5", R.drawable.profile),
            ListModel("Menu 3", "Deskripsi menu 3", R.drawable.baseline_settings_24),
        )

        val adapter = ListAdapter(this, menuList)
        listView.adapter = adapter

        listView.setOnItemClickListener{parent, view, position, id ->
            val selectedItem = menuList[position]

            if(selectedItem.name == "Menu 5"){
                Toast.makeText(this, "Anda klik Menu 5", Toast.LENGTH_SHORT).show()
            }
        }
    }
}