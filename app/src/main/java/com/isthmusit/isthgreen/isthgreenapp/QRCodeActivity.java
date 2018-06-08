package com.isthmusit.isthgreen.isthgreenapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

public class QRCodeActivity extends AppCompatActivity {
     private Button btnNext;
     private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        this.setTitle("QR Code");

        btnNext = (Button)findViewById(R.id.btnGoToSendImage);
        btnNext.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(QRCodeActivity.this, SendImageActivity.class);
            startActivity(intent);
            }
        });

        btnBack = (Button)findViewById(R.id.btnBackToLogin);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

}

