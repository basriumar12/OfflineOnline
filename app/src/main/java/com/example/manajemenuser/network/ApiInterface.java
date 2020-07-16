package com.example.manajemenuser.network;



import com.example.manajemenuser.pojo.ResponseGetAll;
import com.example.manajemenuser.pojo.ResponseInsert;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface ApiInterface {
    @FormUrlEncoded
    @POST("add")
    Call<ResponseInsert> insertData(@Header("key") String key,
                                    @Field("name") String nama,
                                    @Field("email") String email);

    @GET("all")
    Call<ArrayList<ResponseGetAll>> getdata(@Header("key") String key);



}
