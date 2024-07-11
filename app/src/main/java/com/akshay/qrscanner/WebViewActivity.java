package com.akshay.qrscanner;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.akshay.qrscanner.databinding.ActivityWebViewBinding;

import java.util.Objects;

public class WebViewActivity extends AppCompatActivity {
ActivityWebViewBinding binding;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWebViewBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        binding.webView.setWebViewClient(new WebViewClient()); // Ensure links open within the WebView
//        binding.webView.getSettings().setJavaScriptEnabled(true);
//
//        String url = getIntent().getStringExtra("URL");
//        if (url != null) {
//            binding.webView.loadUrl(url);
//        }

        binding.webView.setWebViewClient(new WebViewClient() {
            // This method is currently empty, but you can handle link behavior here if needed
            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                return false; // Returning false loads the URL in the WebView
            }
        });
        Objects.requireNonNull(binding.webView).getSettings().setJavaScriptEnabled(true);

        String url = getIntent().getStringExtra("url");
        if (url != null) {
            binding.webView.loadUrl(url);
        }



    }
}