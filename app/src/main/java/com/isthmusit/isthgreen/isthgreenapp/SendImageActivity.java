package com.isthmusit.isthgreen.isthgreenapp;

import android.content.DialogInterface;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import com.isthmusit.isthgreen.isthgreenapp.entity.User;

public class SendImageActivity extends AppCompatActivity {
    private Button btnNext;
    //private Button btnBack;
    private Button btnOpenCamera;
    private Button btnSelectPhoto;
    private ImageView imageViewSelectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_image);

        this.setTitle("Get Image");

        btnOpenCamera = findViewById(R.id.btnOpenCamera);
        btnOpenCamera.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePicture, 0);
//            AlertDialog.Builder pictureDialog = new AlertDialog.Builder(SendImageActivity.this);
//            pictureDialog.setTitle("Select Action");
//            String[] pictureDialogItems = {
//                    "Select photo from gallery",
//                    "Capture photo from camera" };
//            pictureDialog.setItems(pictureDialogItems,
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            switch (which) {
//                                case 0:
//                                    //choosePhotoFromGallary();
//                                    break;
//                                case 1:
//                                    //takePhotoFromCamera();
//                                    break;
//                            }
//                        }
//                    });
//            pictureDialog.show();
            }
        });

        btnSelectPhoto = findViewById(R.id.btnSelectPhoto);
        btnSelectPhoto.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);
            }
        });

        btnNext = findViewById(R.id.btnGoToResult);
        btnNext.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(SendImageActivity.this, UploadResultActivity.class);
            startActivity(intent);
            }
        });

        imageViewSelectedImage = findViewById(R.id.imageViewSelectedImage);

//        btnBack = (Button)findViewById(R.id.btnBackToQRCode);
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageViewSelectedImage.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageViewSelectedImage.setImageURI(selectedImage);
                }
                break;
        }
    }
}
