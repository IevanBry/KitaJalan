package quiz_1

import Adapter.PagerAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.kitajalan.Activity.fragment.Welcome1Fragment
import com.example.kitajalan.Activity.fragment.Welcome2Fragment
import com.example.kitajalan.Activity.fragment.Welcome3Fragment
import com.example.kitajalan.R
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class LinkTutorialActivity : AppCompatActivity() {
    private lateinit var toolbar : Toolbar
    private lateinit var viewPager : ViewPager2
    private lateinit var dotsIndicator : DotsIndicator
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_link_tutorial)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.toolBarQuiz)
        viewPager = findViewById(R.id.QuizViewPager2)
        dotsIndicator = findViewById(R.id.dotIndicatorQuiz)

        toolbar.setTitle("Dashboard WebView")
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val fragmentList = listOf(Link1Fragment(), Link2Fragment(), Link3Fragment(), Link4Fragment())
        val adapter = LinkAdapter(this, fragmentList)
        viewPager.adapter = adapter

        dotsIndicator.attachTo(viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home->{
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}