package com.isthmusit.isthgreen.isthgreenapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.isthmusit.isthgreen.isthgreenapp.entity.UserSession;
import com.isthmusit.isthgreen.isthgreenapp.service.SessionManager;

public class BaseActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(BaseActivity.this);

        if(!isLogin()){
            Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    protected String getToken(){
        UserSession userSession = sessionManager.getSession();
        return userSession.getToken();
    }

    protected Boolean isLogin(){
        return sessionManager.isLoggedIn();
    }

    protected void setToolbar(String title, Boolean hasBackOption){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(hasBackOption);
        getSupportActionBar().setTitle(title);
        toolbar.inflateMenu(R.menu.user_menu);
        TextView toolBarTitle = findViewById(R.id.toolbarTitle);
        toolBarTitle.setText(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_menu:

                sessionManager.logOut();
                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
