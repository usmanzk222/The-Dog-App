package com.usman.mvvmsample.testhelper

import androidx.paging.PagingSource
import androidx.paging.testing.asPagingSourceFactory
import com.usman.mvvmsample.data.local.IDogBreedsLocalDataSource
import com.usman.mvvmsample.data.local.db.entities.DogBreedsEntity
import com.usman.mvvmsample.data.local.db.entities.RemoteKeys

class FakeLocalDataSource: IDogBreedsLocalDataSource {

    private val localBreeds: MutableList<DogBreedsEntity> = mutableListOf()

    override fun getDogBreeds(): PagingSource<Int, DogBreedsEntity> {
        return localBreeds.asPagingSourceFactory().invoke()
    }

    override suspend fun deleteAllDogBreeds() {
        localBreeds.clear()
    }

    override suspend fun getDogBreed(id: Int): DogBreedsEntity {
        return localBreeds.first { it.id == id }
    }

    override suspend fun insertAllDogBreeds(breeds: List<DogBreedsEntity>) {
        localBreeds.addAll(breeds)
    }

    override suspend fun insertAllKeys(remoteKeys: List<RemoteKeys>) {
        TODO("Not yet implemented")
    }

    override suspend fun remoteKeysBreedId(breedId: Int): RemoteKeys? {
        TODO("Not yet implemented")
    }

    override suspend fun clearRemoteKeys() {
        TODO("Not yet implemented")
    }

    override suspend fun performTransaction(transaction: suspend () -> Unit) {
        transaction()
    }

}