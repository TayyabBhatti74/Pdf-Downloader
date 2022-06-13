package com.example.filedownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText edtlink;
    Button btndownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtlink = findViewById(R.id.edtlink);
        btndownload = findViewById(R.id.btndownload);

        btndownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String geturl = edtlink.getText().toString();
                if (geturl.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter URl", Toast.LENGTH_SHORT).show();
                } else {
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(geturl));
                    String title = URLUtil.guessFileName(geturl, null, null);
                    request.setTitle(title);
                    request.setDescription("Downloading File Please Wait...");
                    String cookie = CookieManager.getInstance().getCookie(geturl);
                    request.addRequestHeader("cookie", cookie);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

                    DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);

                    closeKeyboard();

                    Toast.makeText(MainActivity.this, "Downloading Started", Toast.LENGTH_SHORT).show();
                }


            }

        });
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}