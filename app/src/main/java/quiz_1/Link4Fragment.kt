package quiz_1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.kitajalan.R
class Link4Fragment : Fragment() {
    private lateinit var webView : WebView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_link4, container, false)

        webView = view.findViewById(R.id.WebView4)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://web-based-tutorial.com/")
        webView.webViewClient = WebViewClient()

        return view
    }
}