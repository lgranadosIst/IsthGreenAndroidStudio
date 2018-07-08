package com.isthmusit.isthgreen.isthgreenapp.service;

import com.isthmusit.isthgreen.isthgreenapp.contract.JWTToken;
import com.isthmusit.isthgreen.isthgreenapp.entity.UserCredential;
import com.isthmusit.isthgreen.isthgreenapp.entity.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/api/authenticate")
    Call<JWTToken> authenticate(@Body UserCredential userCredential);

    @GET("/api/posts")
    Call<List<Post>> getPosts();
}
