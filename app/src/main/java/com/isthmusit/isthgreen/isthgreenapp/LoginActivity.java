package com.isthmusit.isthgreen.isthgreenapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.isthmusit.isthgreen.isthgreenapp.contract.JWTToken;
import com.isthmusit.isthgreen.isthgreenapp.entity.User;
import com.isthmusit.isthgreen.isthgreenapp.entity.UserCredential;
import com.isthmusit.isthgreen.isthgreenapp.service.ApiService;
import com.isthmusit.isthgreen.isthgreenapp.service.RetrofitClient;
import com.isthmusit.isthgreen.isthgreenapp.service.SessionManager;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private TextView txtCopyright;
    private Boolean isUsernameValid = false;
    private Boolean isPasswordValid = false;
    private EditText mPasswordView;
    private EditText mUsernameView;
    private CircleImageView isthmusLogo;
    private SessionManager sessionManager;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.setTitle("Login");
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        txtCopyright = findViewById(R.id.txtCopyright);
        txtCopyright.setText("© "+ Integer.toString(year)+" Copyright Isthmus Software");

        mUsernameView = findViewById(R.id.username);
        mPasswordView = findViewById(R.id.password);

        Button mLoginSignInButton = findViewById(R.id.login_button);
        mLoginSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        isthmusLogo = findViewById(R.id.isthmus_logo);
        isthmusLogo.setImageResource(R.mipmap.ic_isthgreen_foreground);

        sessionManager = new SessionManager(LoginActivity.this);
    }

    private void attemptLogin() {
        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            mUsernameView.setError(getString(R.string.error_invalid_username));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if(isUsernameValid && isPasswordValid){
                authenticateToService(username, password);
            }
        }
    }

    private boolean isUsernameValid(String username) {
        //TODO: Replace this with your own logic
        isUsernameValid = username.length() > 2;
        return isUsernameValid;
    }

    private boolean isPasswordValid(String password) {
        isPasswordValid = password.length() > 2;
        return isPasswordValid;
    }

    private void authenticateToService(String username, String password){

        progress = new ProgressDialog(LoginActivity.this);
        progress.setTitle("Authenticating...");
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        progress.show();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitClient.getInstance();
        Retrofit retrofit = builder.client(httpClient.build()).build();
        ApiService apiService = retrofit.create(ApiService.class);

        UserCredential userCredential = new UserCredential(username, password, true);
        Call<JWTToken> call =  apiService.authenticate(userCredential);

//        User user = new User();
//        user.Username = username;
//        user.Password = password;
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("User", user);

        call.enqueue(new Callback<JWTToken>() {
            @Override
            public void onResponse(Call<JWTToken> call, Response<JWTToken> response) {
                if(response != null && response.isSuccessful()){
                    progress.hide();
                    sessionManager.createSession(response.body().getId_token(), "USERID");
                    Intent intent = new Intent(LoginActivity.this, QRCodeActivity.class);
                    //intent.putExtras(bundle);
                    startActivity(intent);
                }
                else{
                    progress.hide();
                    Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JWTToken> call, Throwable t) {

            }
        });
    }
}

