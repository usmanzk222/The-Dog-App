package com.usman.mvvmsample.data.remote

import com.usman.mvvmsample.data.model.NetworkDogBreed
import javax.inject.Inject

class DogBreedsRemoteDataSource @Inject constructor(
    private val serviceGateway: ServiceGateway
) : IDogBreedsRemoteDataSource {

    override suspend fun getDogBreeds(page: Int, limit: Int): List<NetworkDogBreed> {
        return serviceGateway.getDogBreeds(page, limit)
    }
}