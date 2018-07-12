package com.isthmusit.isthgreen.isthgreenapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.isthmusit.isthgreen.isthgreenapp.entity.QRCode;

public class QRCodeActivity extends BaseActivity {

    private Button btnNext;
    private Button btnOpenQrCode;
    private TextView textUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        setToolbar("Scan QR Code", false);
        this.setTitle("Scan QR Code");

        textUsername = findViewById(R.id.TextUsername);
        textUsername.setText("Token: " + getToken());

        btnOpenQrCode = findViewById(R.id.btnOpenQRCode);
        btnOpenQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openQRCode();
            }
        });

        btnNext = findViewById(R.id.btnGoToSendImage);
        btnNext.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QRCodeActivity.this, SendImageActivity.class);
                startActivity(intent);
            }
        });



    }

    private void openQRCode(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan QR Code");
        integrator.setCameraId(0);
        //integrator.setOrientationLocked(false);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
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

                QRCode qrCode = new QRCode();
                qrCode.setQrCode(result.getContents());
                Bundle bundle = new Bundle();
                bundle.putParcelable("QRCode", qrCode);

                Intent intent = new Intent(QRCodeActivity.this, SendImageActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}

