package abdullah.alialdin.coronavirus.ui.news;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import abdullah.alialdin.coronavirus.R;

public class NewsViewerActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_viewer);
        Intent intent = this.getIntent();
        String url = intent.getStringExtra(NewsAdapter.EXTRA_URL);
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        WebView webView = findViewById(R.id.web_view);
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int progress){
                progressBar.setProgress(progress);
                if(progress == 100) {
                    webView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }
        });
    }
}
