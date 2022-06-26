package com.example.tasks.service

import com.google.gson.annotations.SerializedName

class HeaderModel {

    //serializedName serve para captar o nome que é passado pelo servidor
    @SerializedName("token")
    var token: String = ""

    @SerializedName("personKey")
    var personKey: String = ""

    @SerializedName("body")
    var body: String = ""

}