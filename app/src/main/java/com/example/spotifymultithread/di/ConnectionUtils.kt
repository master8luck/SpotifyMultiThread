package com.example.spotifymultithread.di

import android.util.Base64
import java.nio.charset.StandardCharsets

object ConnectionUtils {

    val API_AUTH_ENDPOINT = "https://accounts.spotify.com/api/"
    val API_ENDPOINT = "https://api.spotify.com/v1/"


    private var client_id = "4745bdad4c554fdfa1906eebb652b6bd"
    private var client_secret = "52ac9c9c43944688906e2c160b4839df"


    // Some bad guided(in javascript) magic, but works
    fun genAuthKey(): String =
        "Basic " + String(
            Base64.encode(("$client_id:$client_secret").toByteArray(), Base64.DEFAULT),
            StandardCharsets.UTF_8
        ).replace("\n", "")


}