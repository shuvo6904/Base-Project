package com.example.baseproject.network.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class CommonResponse(
    @SerializedName("message")
    var message: String = "",
    @SerializedName("status")
    var status: Boolean = false,
    @SerializedName("success")
    var success: Boolean = false,

    @SerializedName("otp")
    var otp: Int = 0,

    ): Serializable