package com.isthmusit.isthgreen.isthgreenapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.isthmusit.isthgreen.isthgreenapp.entity.User;

import org.w3c.dom.Text;

public class QRCodeActivity extends AppCompatActivity {
     private Button btnNext;
     private Button btnBack;

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

        /*btnBack = (Button)findViewById(R.id.btnBackToLogin);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });*/

    }

}

