package com.isthmusit.isthgreen.isthgreenapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.isthmusit.isthgreen.isthgreenapp.entity.Post;
import com.isthmusit.isthgreen.isthgreenapp.entity.PostAdapter;
import com.isthmusit.isthgreen.isthgreenapp.service.ApiService;
import com.isthmusit.isthgreen.isthgreenapp.service.RetrofitClient;
import com.isthmusit.isthgreen.isthgreenapp.util.AuthInterceptor;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DashboardActivity extends AppCompatActivity {
    private Button btnBack;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        this.setTitle("Dashboard");

        btnBack = findViewById(R.id.btnBackToQRCode);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, QRCodeActivity.class);
                startActivity(intent);
            }
        });

        setToolbar();

        recyclerView =  findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this);
        getPosts();
    }

    private void setToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");
        TextView toolBarTitle = findViewById(R.id.toolbarTitle);
        toolBarTitle.setText("Dashboard");
    }

    private void getPosts(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = RetrofitClient.getInstance();
        Retrofit retrofit = builder.client(httpClient.addInterceptor(new AuthInterceptor(DashboardActivity.this)).build()).build();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Post>> call =  apiService.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response != null && response.isSuccessful()){
                    postAdapter = new PostAdapter(response.body(),R.layout.post_item, new PostAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Long id, int position) {
                            Toast.makeText(DashboardActivity.this, id.toString(),Toast.LENGTH_LONG).show();
                        }
                    });

                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(postAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }
}
