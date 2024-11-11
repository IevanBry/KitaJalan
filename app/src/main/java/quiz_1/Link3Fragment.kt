package quiz_1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.kitajalan.R
class Link3Fragment : Fragment() {
    private lateinit var webView : WebView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_link3, container, false)

        webView = view.findViewById(R.id.WebView3)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://www.tutorialspoint.com/website_development/index.htm")
        webView.webViewClient = WebViewClient()

        return view
    }
}