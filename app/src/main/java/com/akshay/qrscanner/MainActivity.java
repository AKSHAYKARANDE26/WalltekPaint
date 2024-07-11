package com.akshay.qrscanner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.akshay.qrscanner.databinding.ActivityMainBinding;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        binding.webViewMain.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        binding.webViewMain.getSettings().setJavaScriptEnabled(true);
        binding.webViewMain.loadUrl("https://walltekpaint.com/");

        binding.buttonOpenScanner.setOnClickListener(view -> {
            scanCode();
        });
    }

    private void  scanCode(){
        ScanOptions options=new ScanOptions();
        options.setPrompt("volume up flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureActivity.class);
        barLauncher.launch(options);
    }
    ActivityResultLauncher<ScanOptions> barLauncher=registerForActivityResult(new ScanContract(),result->{
       if (result.getContents()!=null){
           String scannedContent = result.getContents();
           // Check if scanned content is a valid URL
           if (isValidUrl(scannedContent)) {
               openWebviewActivity(scannedContent);
           } else {
               AlertDialog.Builder builder = new AlertDialog.Builder(this);
               builder.setTitle("Result");
               builder.setMessage(scannedContent);
               builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());
               builder.show();
           }
//           AlertDialog.Builder builder=new AlertDialog.Builder(this);
//           builder.setTitle("Result got ");
//           builder.setMessage(result.getContents());
//           builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//               @Override
//               public void onClick(DialogInterface dialogInterface, int i) {
//                   dialogInterface.dismiss();
//               }
//           }).show();
       }
    });

    // Method to check if the string is a valid URL
    private boolean isValidUrl(String url) {
        try {
            Uri.parse(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Method to open a new activity with the WebView
    private void openWebviewActivity(String url) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (binding.webViewMain.canGoBack()) {
            binding.webViewMain.goBack();
        } else {
            super.onBackPressed();
        }
    }
}