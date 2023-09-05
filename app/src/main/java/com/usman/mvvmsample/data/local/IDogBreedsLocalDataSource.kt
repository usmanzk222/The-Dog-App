package com.usman.mvvmsample.data.local

import androidx.paging.PagingSource
import com.usman.mvvmsample.data.local.db.entities.DogBreedsEntity
import com.usman.mvvmsample.data.local.db.entities.RemoteKeys

interface IDogBreedsLocalDataSource {

    fun getDogBreeds(): PagingSource<Int, DogBreedsEntity>
    suspend fun deleteAllDogBreeds()
    suspend fun getDogBreed(id: Int): DogBreedsEntity
    suspend fun insertAllDogBreeds(breeds: List<DogBreedsEntity>)


    suspend fun insertAllKeys(remoteKeys: List<RemoteKeys>)
    suspend fun remoteKeysBreedId(breedId: Int): RemoteKeys?
    suspend fun clearRemoteKeys()

    suspend fun performTransaction(transaction:suspend () -> Unit)
}