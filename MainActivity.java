package ir.sadeghati.business;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.view.Gravity;

public class MainActivity extends Activity {
    private WebView webView;
    private static final String HOME_URL = "https://lucent-mooncake-da5f38.netlify.app/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(6, 26, 60));
        getWindow().setNavigationBarColor(Color.rgb(6, 26, 60));

        FrameLayout root = new FrameLayout(this);
        webView = new WebView(this);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true); // localStorage persistence for app data
        settings.setDatabaseEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setAllowFileAccess(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setSupportMultipleWindows(false);
        webView.setBackgroundColor(Color.WHITE);
        webView.setWebViewClient(new WebViewClient() {
            @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                return !(url.startsWith("https://lucent-mooncake-da5f38.netlify.app/") || url.startsWith("https://"));
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        root.addView(webView, new FrameLayout.LayoutParams(-1, -1));
        setContentView(root);

        if (savedInstanceState == null) webView.loadUrl(HOME_URL);
        else webView.restoreState(savedInstanceState);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        if (webView != null) webView.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }

    @Override protected void onDestroy() {
        if (webView != null) { webView.stopLoading(); webView.destroy(); webView = null; }
        super.onDestroy();
    }
}
