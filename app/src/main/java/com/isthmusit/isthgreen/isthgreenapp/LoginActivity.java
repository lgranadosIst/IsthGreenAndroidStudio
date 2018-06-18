package com.isthmusit.isthgreen.isthgreenapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.isthmusit.isthgreen.isthgreenapp.entity.User;
import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    private TextView txtCopyright;
    private Boolean isUsernameValid = false;
    private Boolean isPasswordValid = false;
    private EditText mPasswordView;
    private EditText mUsernameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.setTitle("Login");
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        txtCopyright = findViewById(R.id.txtCopyright);
        txtCopyright.setText("© "+ Integer.toString(year)+" Copyright Isthmus Software");

        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mLoginSignInButton = (Button) findViewById(R.id.login_button);
        mLoginSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
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
                Intent intent = new Intent(LoginActivity.this, QRCodeActivity.class);

                User user = new User();
                user.Username = username;
                user.Password = password;
                Bundle bundle = new Bundle();
                bundle.putParcelable("User", user);
                intent.putExtras(bundle);

                startActivity(intent);
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
}

