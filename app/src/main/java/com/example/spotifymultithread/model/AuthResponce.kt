package com.example.spotifymultithread.model

import com.google.gson.annotations.SerializedName

class AuthResponce {

    @SerializedName("access_token")
    var token: String? = null
    @SerializedName("token_type")
    var type: String? = null

}