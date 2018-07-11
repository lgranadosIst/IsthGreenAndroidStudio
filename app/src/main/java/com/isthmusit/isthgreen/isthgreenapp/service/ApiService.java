package com.isthmusit.isthgreen.isthgreenapp.service;

import com.isthmusit.isthgreen.isthgreenapp.contract.JWTToken;
import com.isthmusit.isthgreen.isthgreenapp.entity.ImageResponse;
import com.isthmusit.isthgreen.isthgreenapp.entity.RecycleRequest;
import com.isthmusit.isthgreen.isthgreenapp.entity.UserCredential;
import com.isthmusit.isthgreen.isthgreenapp.entity.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import okhttp3.MultipartBody;

public interface ApiService {

    @POST("/api/authenticate")
    Call<JWTToken> authenticate(@Body UserCredential userCredential);

    @GET("/api/posts")
    Call<List<Post>> getPosts();

    @POST("/api/register-users")
    Call<RecycleRequest> registerUsers(@Body RecycleRequest request);

    @Multipart
    @POST("/upload")
    Call<ImageResponse> postImage(@Part MultipartBody.Part image);
}
