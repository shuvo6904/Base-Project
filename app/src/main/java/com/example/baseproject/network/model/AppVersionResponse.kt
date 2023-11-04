package com.example.baseproject.network.model


import com.google.gson.annotations.SerializedName

data class AppVersionResponse(
    @SerializedName("data")
    val `data`: Data? = null
): CommonResponse() {
    data class Data(
        @SerializedName("app_close")
        val appClose: Boolean? = false,
        @SerializedName("app_close_reason_message")
        val appCloseReasonMessage: String? = null,
        @SerializedName("current_version_code")
        val currentVersionCode: Int? = null,
        @SerializedName("current_version_name")
        val currentVersionName: String? = null,
        @SerializedName("is_force_update")
        val isForceUpdate: Boolean? = false,
        @SerializedName("last_update")
        val lastUpdate: String? = null,
        @SerializedName("operating_system_id")
        val operatingSystemId: Int? = null
    )
}