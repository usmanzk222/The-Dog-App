package com.usman.mvvmsample.data.remote

import com.usman.mvvmsample.data.model.NetworkDogBreed

interface IDogBreedsRemoteDataSource {

    suspend fun getDogBreeds(page: Int, limit: Int): List<NetworkDogBreed>

}

