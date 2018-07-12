package com.isthmusit.isthgreen.isthgreenapp;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

public class UploadResultActivity extends BaseActivity {
    private Button btnNext;
    private TextView textImageUrl;
    private TextView textImageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_result);

        setToolbar("Recycle Request Result", true);
        this.setTitle("Recycle Request Result");

        btnNext = findViewById(R.id.btnGoToDashboard);
        btnNext.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(UploadResultActivity.this, DashboardActivity.class);
            startActivity(intent);
            }
        });

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

}
