package com.isthmusit.isthgreen.isthgreenapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.isthmusit.isthgreen.isthgreenapp.entity.QRCode;
import com.isthmusit.isthgreen.isthgreenapp.entity.User;
import com.isthmusit.isthgreen.isthgreenapp.entity.UserSession;
import com.isthmusit.isthgreen.isthgreenapp.service.SessionManager;


public class QRCodeActivity extends AppCompatActivity {

    private Button btnNext;
    private Button btnOpenQrCode;
    private TextView textUsername;
    private TextView textPassword;
    private Toolbar toolbar;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        this.setTitle("QR Code");

        sessionManager = new SessionManager(QRCodeActivity.this);
        UserSession userSession = sessionManager.getSession();
        boolean login = sessionManager.isLoggedIn();
        //sessionManager.logOut();

//        Bundle bundle = this.getIntent().getExtras();
//        if(bundle != null){
//            User user = bundle.getParcelable("User");
//            textUsername = findViewById(R.id.TextUsername);
//            textPassword = findViewById(R.id.TextPassword);
//            textUsername.setText("Username: " + user.Username + " Token: " + userSession.getToken());
//            textPassword.setText("Password: " + user.Password + " UserId: " + userSession.getUserId());
//        }

        textUsername = findViewById(R.id.TextUsername);
        textUsername.setText("Token: " + userSession.getToken());

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

        setToolbar();

    }

    private void setToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("QR Code");
        TextView toolBarTitle = findViewById(R.id.toolbarTitle);
        toolBarTitle.setText("QR Code");
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

