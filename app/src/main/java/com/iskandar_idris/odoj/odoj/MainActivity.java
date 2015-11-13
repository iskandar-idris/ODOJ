package com.iskandar_idris.odoj.odoj;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import java.io.File;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {
    int backButtonCount = 0;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar)findViewById(R.id.toolbar); // <-- setup views after
        setSupportActionBar(mToolbar);
        getSupportActionBar().setIcon(R.drawable.odoj_launcher);

        setTitle(Html.fromHtml("<font color='#ffffff'>ODOJER ACCOUNT </font>"));
        WebView webViewodojorg = (WebView) findViewById(R.id.webViewodojorg);
        // Enable javascript
        webViewodojorg.getSettings().setJavaScriptEnabled(true);
        webViewodojorg.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });
        webViewodojorg.loadUrl("http://www.onedayonejuz.org/login#wrap-box-login");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info){
            Intent i = new Intent(this, InformasiActivity.class);
            startActivity(i);
        }
        if (id == R.id.action_update) {
            WebView webViewodojorg = (WebView) findViewById(R.id.webViewodojorg);
            // Enable javascript
            webViewodojorg.getSettings().setJavaScriptEnabled(true);
            webViewodojorg.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // do your handling codes here, which url is the requested url
                    // probably you need to open that url rather than redirect:
                    view.loadUrl(url);
                    return false; // then it is not handled by default action
                }
            });
            webViewodojorg.loadUrl("http://odoj-account-update.iskandar-idris.com/");
            final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

            // This is where downloaded files will be written, using the package name isn't required
            // but it's a good way to communicate who owns the directory
            final File destinationDir = new File (Environment.getExternalStorageDirectory()+"/download");
            if (!destinationDir.exists()) {
                destinationDir.mkdir(); // Don't forget to make the directory if it's not there
            }
            webViewodojorg.setWebViewClient(new WebViewClient() {
                @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    boolean shouldOverride = false;
                    // We only want to handle requests for mp3 files, everything else the webview
                    // can handle normally
                    if (url.endsWith(".apk")) {
                        shouldOverride = true;
                        Uri source = Uri.parse(url);

                        // Make a new request pointing to the mp3 url
                        DownloadManager.Request request = new DownloadManager.Request(source);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        //request.setAllowedNetworkTypes(
                           //     DownloadManager.Request.NETWORK_WIFI
                             //           | DownloadManager.Request.NETWORK_MOBILE)
                               // .setAllowedOverRoaming(false).setTitle("Demo");
                        // Use the same file name for the destination
                        File destinationFile = new File(destinationDir, source.getLastPathSegment());
                        request.setDestinationUri(Uri.fromFile(destinationFile));
                        // Add it to the manager
                        manager.enqueue(request);

                    }
                    return shouldOverride;
                }
            });
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Toast.makeText(this, "Sekarang sedang menggunakan versi 1.0", Toast.LENGTH_SHORT).show();
        }
        if (id == 16908332) {
            WebView webViewodojorg = (WebView) findViewById(R.id.webViewodojorg);
            // Enable javascript
            webViewodojorg.getSettings().setJavaScriptEnabled(true);
            webViewodojorg.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // do your handling codes here, which url is the requested url
                    // probably you need to open that url rather than redirect:
                    view.loadUrl(url);
                    return false; // then it is not handled by default action
                }
            });
            webViewodojorg.loadUrl("http://www.onedayonejuz.org/dashboard/index");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (backButtonCount >= 1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Tekan tombol kembali sekali lagi untuk menutup aplikasi.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}
