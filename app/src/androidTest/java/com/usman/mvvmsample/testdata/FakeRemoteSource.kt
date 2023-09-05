package com.usman.mvvmsample.testdata

import com.usman.mvvmsample.data.model.NetworkDogBreed
import com.usman.mvvmsample.data.remote.IDogBreedsRemoteDataSource
import java.io.IOException

class FakeRemoteSource: IDogBreedsRemoteDataSource {

    var failureMsg: String? = null
    private val dogBreeds = mutableListOf<NetworkDogBreed>()

    fun addDogBreed(dogBreed: NetworkDogBreed){
        dogBreeds.add(dogBreed)
    }

    fun clearDogBreeds() = dogBreeds.clear()

    override suspend fun getDogBreeds(page: Int, limit: Int): List<NetworkDogBreed> {
        failureMsg?.let {
            throw IOException(it)
        }
        val startIndex = page * limit

        if(startIndex > dogBreeds.size - 1)
            return listOf()

        return dogBreeds.subList(startIndex, Integer.min(dogBreeds.size, startIndex + limit))
    }

}