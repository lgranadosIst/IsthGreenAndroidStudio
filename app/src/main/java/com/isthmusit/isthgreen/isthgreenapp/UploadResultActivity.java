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
