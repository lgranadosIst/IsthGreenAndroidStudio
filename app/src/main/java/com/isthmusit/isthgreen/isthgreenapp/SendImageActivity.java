package com.isthmusit.isthgreen.isthgreenapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

public class SendImageActivity extends AppCompatActivity {
    private Button btnNext;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_image);

        this.setTitle("Get Image");

        btnNext = (Button)findViewById(R.id.btnGoToResult);
        btnNext.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(SendImageActivity.this, UploadResultActivity.class);
            startActivity(intent);
            }
        });

        btnBack = (Button)findViewById(R.id.btnBackToQRCode);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
