package com.example.baseproject.network.model


import com.google.gson.annotations.SerializedName

class ConvertLatLngCommonResponse : ArrayList<ConvertLatLngCommonResponse.ConvertLatLngCommonResponseItem>(){
    data class ConvertLatLngCommonResponseItem(
        @SerializedName("Address1")
        val address1: String? = null,
        @SerializedName("Address2")
        val address2: String? = null,
        @SerializedName("AreaOrUpzilaName")
        val areaOrUpzilaName: String? = null,
        @SerializedName("CountryName")
        val countryName: String? = null,
        @SerializedName("Id")
        val id: Long? = null,
        @SerializedName("Latitude")
        val latitude: Double? = null,
        @SerializedName("Longitude")
        val longitude: Double? = null,
        @SerializedName("StateProvinceName")
        val stateProvinceName: String? = null
    )
}