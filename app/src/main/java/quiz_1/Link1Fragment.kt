package quiz_1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.kitajalan.R

class Link1Fragment : Fragment() {

    private lateinit var webView : WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_link1, container, false)

        webView = view.findViewById(R.id.WebView1)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://codefinity.com/")
        webView.webViewClient = WebViewClient()

        return view
    }
}