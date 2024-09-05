package com.example.okhttpdemo;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);

        // 创建OkHttpClient实例
        OkHttpClient client = new OkHttpClient();

        // 创建一个Request
        Request request = new Request.Builder()
                .url("http://192.168.1.52:8080/base64/aGVsbG8gd29ybGQ=") // 替换成你要请求的URL
                .build();

        // 异步请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 请求失败时调用
                showMessage("请求失败: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 请求成功时调用
                if (response.isSuccessful()) {
                    // 从响应体中读取字符串
                    String responseBody = response.body().string();
                    Log.d(TAG, "onResponse: responseBody == "+responseBody);
                    showMessage("请求成功："+responseBody);

                } else {
                    showMessage("请求未成功: " + response.code());
                }
            }
        });
    }

    private void showMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(message);
            }
        });
    }
}