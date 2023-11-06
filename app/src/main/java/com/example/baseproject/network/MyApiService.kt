package com.example.baseproject.network

import com.example.baseproject.network.model.ConvertLatLngCommonResponse
import com.example.baseproject.utils.API_HEADERS
import com.example.baseproject.utils.GET_CUSTOMER_ADDRESS
import com.example.baseproject.utils.POST_CUSTOMER_ADDRESS
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path
interface MyApiService {
    @GET("$GET_CUSTOMER_ADDRESS{pageNo}")
    suspend fun getCustomerAddress(
        @HeaderMap headers: Map<String,String> = API_HEADERS,
        @Path("pageNo") pageNo: Int
    ): ConvertLatLngCommonResponse
    @POST(POST_CUSTOMER_ADDRESS)
    suspend fun postCustomerAddress(
        @HeaderMap headers: Map<String,String> = API_HEADERS,
        @Body body: ArrayList<ConvertLatLngCommonResponse.ConvertLatLngCommonResponseItem>
    ): Response<Void>
}