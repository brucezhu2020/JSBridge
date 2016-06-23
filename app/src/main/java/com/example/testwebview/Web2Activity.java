package com.example.testwebview;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Web2Activity extends AppCompatActivity {

    private static final String TAG = Web1Activity.class.getSimpleName();
    private WebView webView = null;
    private String file = "file:///android_asset/test2.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web2);
        Log.e(TAG," onCreate = "+Thread.currentThread().getId());
        init();
    }

    private void init() {
        webView = (WebView) findViewById(R.id.webview);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                Log.e(TAG,"newProgress = "+newProgress);


            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {

                Log.e(TAG,"url = "+url +"message = "+message);
                if(message != null && message.startsWith("toast")){
                    result.cancel();
                    showToast();
                    return true;
                }

                return super.onJsPrompt(view, url, message, defaultValue, result);


            }


        });
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e(TAG,"onPageStarted = "+url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e(TAG,"onPageFinished = "+url);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        WebSettings setting =  webView.getSettings();
        setting.setJavaScriptEnabled(true);
        webView.loadUrl(file);

        findViewById(R.id.but1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("javascript:test3('测试3 java调用js')");

            }
        });
    }

    public void showToast(){
        Toast.makeText(this,"测试调用本地方法",Toast.LENGTH_SHORT).show();

    }
}
