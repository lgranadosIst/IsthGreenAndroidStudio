package com.isthmusit.isthgreen.isthgreenapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.isthmusit.isthgreen.isthgreenapp.entity.QRCode;

public class SendImageActivity extends AppCompatActivity {
    private Button btnNext;
    private TextView textQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_image);

        this.setTitle("Get Image");

        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            QRCode qrCode = bundle.getParcelable("QRCode");
            textQRCode = findViewById(R.id.textQRCode);
            textQRCode.setText(qrCode.getQrCode());
        }


        btnNext = findViewById(R.id.btnGoToResult);
        btnNext.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(SendImageActivity.this, UploadResultActivity.class);
            startActivity(intent);
            }
        });

    }
}
