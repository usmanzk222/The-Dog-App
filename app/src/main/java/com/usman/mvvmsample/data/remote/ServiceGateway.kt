package com.usman.mvvmsample.data.remote

import com.usman.mvvmsample.data.model.NetworkDogBreed
import com.usman.mvvmsample.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ServiceGateway {
    @Headers("x-api-key:${Constants.API_KEY}")
    @GET("breeds")
    suspend fun getDogBreeds(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<NetworkDogBreed>
}
