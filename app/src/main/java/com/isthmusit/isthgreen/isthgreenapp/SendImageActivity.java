package com.isthmusit.isthgreen.isthgreenapp;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.net.Uri;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.isthmusit.isthgreen.isthgreenapp.entity.ImageResponse;
import com.isthmusit.isthgreen.isthgreenapp.entity.QRCode;

import com.isthmusit.isthgreen.isthgreenapp.entity.RecycleRequest;
import com.isthmusit.isthgreen.isthgreenapp.service.ApiService;
import com.isthmusit.isthgreen.isthgreenapp.service.RetrofitClient;
import com.isthmusit.isthgreen.isthgreenapp.util.AuthInterceptor;
import com.isthmusit.isthgreen.isthgreenapp.util.FileUtils;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SendImageActivity extends BaseActivity {
    private Button btnNext;
    private Button btnOpenCamera;
    private Button btnSelectPhoto;
    private ImageView imageViewSelectedImage;
    private EditText editQRCode;
    private EditText editAmount;
    private TextView textSelectImage;
    private ProgressDialog progress;

    private Uri filePath;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private static final int CAMERA_PERMISSION_CODE = 1888;

    private Uri fileUri;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_image);

        setToolbar("Get Image Info", true);
        this.setTitle("Get Image Info");

        imageViewSelectedImage = findViewById(R.id.imageViewSelectedImage);
        editAmount = findViewById(R.id.editAmount);
        textSelectImage = findViewById(R.id.textSelectImage);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            QRCode qrCode = bundle.getParcelable("QRCode");
            editQRCode = findViewById(R.id.editQRCode);
            editQRCode.setText(qrCode.getQrCode());
        }

        requestCameraPermission();

        btnOpenCamera = findViewById(R.id.btnOpenCamera);
        btnOpenCamera.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);
            }
        });

        btnSelectPhoto = findViewById(R.id.btnSelectPhoto);
        btnSelectPhoto.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestStoragePermission();
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);
            }
        });

        btnNext = findViewById(R.id.btnGoToResult);
        btnNext.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View focusView = null;
                Boolean cancel = false;
                String amount = editAmount.getText().toString();

                if (imageViewSelectedImage.getDrawable() == null) {
                    textSelectImage.setTextColor(getResources().getColor(R.color.red));
                    imageViewSelectedImage.setBackgroundResource(R.drawable.layout_round_red);
                    focusView = imageViewSelectedImage;
                    cancel = true;
                }
                else if (TextUtils.isEmpty(amount)) {
                    editAmount.setError(getString(R.string.error_field_required));
                    focusView = editAmount;
                    cancel = true;
                }
                if (cancel) {
                    focusView.requestFocus();
                } else {

                    try {
                        uploadMultipart();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null) {
            textSelectImage.setTextColor(Color.DKGRAY);
            imageViewSelectedImage.setBackgroundResource(android.R.color.transparent);

            filePath = data.getData();
            if (requestCode == 0) {
                if (resultCode == Activity.RESULT_OK) {

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        Transformation transformation = new RoundedTransformationBuilder()
                                .borderColor(R.color.colorPrimary)
                                .borderWidthDp(3)
                                .cornerRadiusDp(25)
                                .oval(false)
                                .build();
                        Picasso.with(this)
                                .load(filePath)
                                .fit()
                                .centerCrop()
                                .transform(transformation)
                                .into(imageViewSelectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    Transformation transformation = new RoundedTransformationBuilder()
                            .borderColor(R.color.colorPrimary)
                            .borderWidthDp(3)
                            .cornerRadiusDp(25)
                            .oval(false)
                            .build();
                    Picasso.with(this)
                            .load(filePath)
                            .fit()
                            .centerCrop()
                            .transform(transformation)
                            .into(imageViewSelectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void uploadMultipart() throws IOException{
        if(filePath == null){

        }else{
            progress = new ProgressDialog(SendImageActivity.this);
            progress.setTitle("Submitting Image...");
            progress.setCancelable(false);
            progress.setIndeterminate(true);
            progress.show();
            String path = FileUtils.getPath(SendImageActivity.this,filePath);
            File file = new File(path);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            Retrofit.Builder builder = RetrofitClient.getInstance("https://mediaimage.herokuapp.com/");
            Retrofit retrofit = builder.client(httpClient.build()).build();
            ApiService mediaService =  retrofit.create(ApiService.class);

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image", new Date().toString()+file.getName(), reqFile);

            Call<ImageResponse> call = mediaService.postImage(body);

            call.enqueue(new Callback<ImageResponse>() {
                @Override
                public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                    if(response != null && response.isSuccessful()){
                        //Toast.makeText(SendImageActivity.this, response.body().getImagUrl(), Toast.LENGTH_LONG).show();
                        System.out.println("Image successfully submitted.");
                        submitRecycleRequest(response.body().getImagUrl());
                    }
                    else {
                        Toast.makeText(SendImageActivity.this, "Image submission failed.", Toast.LENGTH_LONG).show();
                        System.out.println("Error on Image submission:");
                    }
                }
                @Override
                public void onFailure(Call<ImageResponse> call, Throwable t) {
                    if(call != null){
                        progress.hide();
                        t.printStackTrace();
                        Toast.makeText(SendImageActivity.this, "Image submission failed.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void submitRecycleRequest(String imageUrl){
        System.out.println("Submitting recycle request...");
        progress.setTitle("Submitting Request...");

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitClient.getInstance();
        Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(SendImageActivity.this)).build()).build();
        ApiService apiService = retrofit.create(ApiService.class);

        RecycleRequest request = new RecycleRequest();
        request.setCant(editAmount.getText().toString());
        request.setImage(imageUrl);
        request.setDate(new Date().toString());
        request.setIdUser((long)123);
        request.setTotal("1");
        request.setType("qr code");
        Call<RecycleRequest> call = apiService.registerUsers(request);

        call.enqueue(new Callback<RecycleRequest>() {
            @Override
            public void onResponse(Call<RecycleRequest> call, Response<RecycleRequest> response) {
                progress.hide();
                if(response != null && response.isSuccessful()){
                    System.out.println("Recycle request successfully submitted:");

                    Intent intent = new Intent(SendImageActivity.this, UploadResultActivity.class);
                    intent.putExtra("ImageId", response.body().getId());
                    intent.putExtra("ImageUrl", response.body().getImage());
                    startActivity(intent);
                }
                else{
                    System.out.println("Error on recycle request submission:");
                    System.out.println(response.message());
                    Toast.makeText(SendImageActivity.this, "Recycle submission failed.", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<RecycleRequest> call, Throwable t) {
                progress.hide();
                System.out.println("Error on recycle request submission:");
                Toast.makeText(SendImageActivity.this, "Recycle submission failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void requestStoragePermission(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){}
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    private void requestCameraPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            return;
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)){}
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
    }

}
