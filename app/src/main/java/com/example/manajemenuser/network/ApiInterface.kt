package com.example.manajemenuser.network

import com.example.manajemenuser.pojo.ResponseGetAll
import com.example.manajemenuser.pojo.ResponseInsert
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface ApiInterface {
    @FormUrlEncoded
    @POST("add")
    fun insertData(
        @Header("key") key: String,
        @Field("name") nama: String,
        @Field("email") email: String
    ): Call<ResponseInsert>

    @GET("all")
    fun getdata(@Header("key") key: String): Call<ArrayList<ResponseGetAll>>
}