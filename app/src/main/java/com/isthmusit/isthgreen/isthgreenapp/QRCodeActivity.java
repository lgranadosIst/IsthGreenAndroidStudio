package com.isthmusit.isthgreen.isthgreenapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import com.isthmusit.isthgreen.isthgreenapp.entity.User;



public class QRCodeActivity extends AppCompatActivity {

     private Button btnNext;
     private Button btnQRCode;

     private TextView textUsername;
     private TextView textPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        this.setTitle("QR Code");

        btnNext = findViewById(R.id.btnGoToSendImage);
        btnNext.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(QRCodeActivity.this, SendImageActivity.class);
            startActivity(intent);
            }
        });

        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            User user = bundle.getParcelable("User");
            textUsername = findViewById(R.id.TextUsername);
            textPassword = findViewById(R.id.TextPassword);
            textUsername.setText("Username: " + user.Username);
            textPassword.setText("Password: " + user.Password);
        }

        IntentIntegrator intent = new IntentIntegrator(this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);

        intent.setPrompt("Scan barcode");
        intent.setCameraId(0);
        intent.setBeepEnabled(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this, "You  canceled the scann", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}

