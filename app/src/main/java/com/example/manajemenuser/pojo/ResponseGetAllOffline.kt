package com.example.manajemenuser.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Awesome Pojo Generator
 */
class ResponseGetAllOffline(
    @field:Expose @field:SerializedName("name") var name: String,
    @field:Expose @field:SerializedName("email") var email: String,
    var date: String,
    var status: Int
) {

    @SerializedName("id")
    @Expose
    var id: Int? = null

}