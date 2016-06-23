package com.example.testwebview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Web1Activity extends AppCompatActivity {

    private static final String TAG = Web1Activity.class.getSimpleName();

    private WebView webView = null;
    private String file = "file:///android_asset/test1.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web1);
        Log.e(TAG," onCreate = "+Thread.currentThread().getId());
        init();
    }

    @SuppressLint("JavascriptInterface")
    private void init() {
        webView = (WebView) findViewById(R.id.webview);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                Log.e(TAG,"newProgress = "+newProgress);
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



        });
        webView.addJavascriptInterface(new JSUtil(this),"android");
        WebSettings setting =  webView.getSettings();
        setting.setJavaScriptEnabled(true);
        webView.loadUrl(file);


        findViewById(R.id.but1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("javascript:test3('java调用js')");

            }
        });
    }

    class JSUtil{

        private Context con;

        public JSUtil(Context con) {
            this.con = con;
        }

        @JavascriptInterface
        public void toast(String msg){
            Log.e("JSUtil","toast = "+Thread.currentThread().getId());
            Toast.makeText(con, ""+msg, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public String getName( ){
            Log.e("JSUtil","getName = "+Thread.currentThread().getId());
            return "tom";
        }

    }

}
