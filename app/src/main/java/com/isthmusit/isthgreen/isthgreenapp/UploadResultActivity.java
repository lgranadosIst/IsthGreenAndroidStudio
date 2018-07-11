package com.isthmusit.isthgreen.isthgreenapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class UploadResultActivity extends AppCompatActivity {
    private Button btnNext;
    private Button btnBack;
    private Toolbar toolbar;
    private TextView textImageUrl;
    private TextView textImageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_result);

        this.setTitle("Result");

        btnNext = findViewById(R.id.btnGoToDashboard);
        btnNext.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(UploadResultActivity.this, DashboardActivity.class);
            startActivity(intent);
            }
        });

        setToolbar();

        textImageUrl = findViewById(R.id.textImageUrl);
        Bundle bundle = getIntent().getExtras();
        if(bundle.containsKey("ImageUrl")) {
            textImageUrl.setText("Image Url: " + bundle.getString("ImageUrl"));
        }
        textImageId = findViewById(R.id.textImageId);
        if(bundle.containsKey("ImageId")) {
            textImageId.setText("Image Id: " + bundle.getLong("ImageId"));
        }
    }

    private void setToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Result");
        TextView toolBarTitle = findViewById(R.id.toolbarTitle);
        toolBarTitle.setText("Result");

    }

}
