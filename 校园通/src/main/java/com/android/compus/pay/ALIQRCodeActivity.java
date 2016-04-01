package com.android.compus.pay;
import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.compus.R;

/**
 * ����չʾAli��Ƕ��ά��
 */
public class ALIQRCodeActivity extends Activity {
    private WebView aliQRCode;
    private String aliQRURL;
    private String aliQRHtml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aliqrcode);


        aliQRCode = (WebView) findViewById(R.id.aliQRCode);
        WebSettings webSettings = aliQRCode.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // ���ÿ���֧������
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);
        //����Ĭ�ϼ��صĿ��ӷ�Χ�Ǵ���Ұ��Χ
        webSettings.setLoadWithOverviewMode(true);
        // ���ó������Ź���
        webSettings.setBuiltInZoomControls(true);


        aliQRCode.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Here put your code
                //Log.w("My Webview", url);

                // return true; //Indicates WebView to NOT load the url;
                return false; //Allow WebView to load url
            }

            //������д
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // Ignore SSL certificate errors
            }
        });

        aliQRHtml = getIntent().getStringExtra("aliQRHtml");
        aliQRURL = getIntent().getStringExtra("aliQRURL");

        //����
        //aliQRCode.loadUrl(aliQRURL);

        //����
        aliQRCode.loadData(aliQRHtml, "text/html", "utf-8");

    }
}
