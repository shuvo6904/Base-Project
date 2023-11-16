package com.example.baseproject.ui.activity.viewmodel

import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.network.MyApiService
import com.example.baseproject.network.model.ConvertLatLngCommonResponse
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.logicbase.livex.LiveEvent
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MyViewModel @Inject constructor(private val myApiService: MyApiService) : ViewModel() {

    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val _addressResponse = LiveEvent<ConvertLatLngCommonResponse>()
    var addressResponse: LiveData<ConvertLatLngCommonResponse> = _addressResponse

    private val _postAddressResponse = LiveEvent<Int>()
    var postAddressResponse: LiveData<Int> = _postAddressResponse

    private val _latLngResponse= LiveEvent<ConvertLatLngCommonResponse.ConvertLatLngCommonResponseItem>()
    var latLngResponse: LiveData<ConvertLatLngCommonResponse.ConvertLatLngCommonResponseItem> = _latLngResponse
    fun getCustomerAddress(pageNo: Int) {
        viewModelScope.launch {
            try {
                val data = myApiService.getCustomerAddress(pageNo = pageNo)
                _addressResponse.value = data
                Log.d("address_response", gson.toJson(data))

            } catch (e: Exception) {
                Log.d("address_error", "exception: ${e.message.toString()}")
            }
        }
    }

    fun postCustomerAddress(body: ArrayList<ConvertLatLngCommonResponse.ConvertLatLngCommonResponseItem>) {
        viewModelScope.launch {
            try {
                val response = myApiService.postCustomerAddress(body = body)
                if (response.isSuccessful) _postAddressResponse.value = response.code()
                Log.d("post_address_response", response.code().toString())
            } catch (e: Exception) {
                Log.d("post_address_error", "exception: ${e.message.toString()}")
            }
        }
    }

    fun getAddressByLatLng(
        geoCoder: Geocoder,
        convertLatLngCommonResponseItem: ConvertLatLngCommonResponse.ConvertLatLngCommonResponseItem
    ) {
        viewModelScope.launch {
            var lat: Double? = null
            var lng: Double? = null
            try {
                val addresses = geoCoder.getFromLocationName(convertLatLngCommonResponseItem.address1 ?: "", 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses.first()
                    lat = address.latitude
                    lng = address.longitude
                } else {
                    val addresses = geoCoder.getFromLocationName(
                        "${convertLatLngCommonResponseItem.address1}, ${convertLatLngCommonResponseItem.areaOrUpzilaName}, ${convertLatLngCommonResponseItem.stateProvinceName}, ${convertLatLngCommonResponseItem.countryName}",
                        1
                    )
                    if (!addresses.isNullOrEmpty()) {
                        val address = addresses.first()
                        lat = address.latitude
                        lng = address.longitude
                    }
                }

            } catch (exception: Exception) {
                Log.d("getLatLng_error", "exception: ${exception.message.toString()}")
            } finally {
                val item = ConvertLatLngCommonResponse.ConvertLatLngCommonResponseItem(
                    address1 = convertLatLngCommonResponseItem.address1,
                    address2 = convertLatLngCommonResponseItem.address2,
                    areaOrUpzilaName = convertLatLngCommonResponseItem.areaOrUpzilaName,
                    countryName = convertLatLngCommonResponseItem.countryName,
                    id = convertLatLngCommonResponseItem.id,
                    latitude = lat,
                    longitude = lng,
                    stateProvinceName = convertLatLngCommonResponseItem.stateProvinceName
                )
                Log.d("address_object", gson.toJson(item))
                _latLngResponse.value = item
            }
        }
    }
}
