package com.usman.mvvmsample.data.local

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.usman.mvvmsample.data.local.db.DogAppDatabase
import com.usman.mvvmsample.data.local.db.daos.DogBreedsDao
import com.usman.mvvmsample.data.local.db.daos.RemoteKeysDao
import com.usman.mvvmsample.data.local.db.entities.DogBreedsEntity
import com.usman.mvvmsample.data.local.db.entities.RemoteKeys
import javax.inject.Inject

class DogBreedsLocalDataSource @Inject constructor(
    private val dogBreedsDao: DogBreedsDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val database: DogAppDatabase
) : IDogBreedsLocalDataSource {

    override suspend fun insertAllDogBreeds(breeds: List<DogBreedsEntity>) {
        dogBreedsDao.insertAll(breeds)
    }

    override fun getDogBreeds(): PagingSource<Int, DogBreedsEntity> {
        return dogBreedsDao.getAllDogBreeds()
    }

    override suspend fun deleteAllDogBreeds(){
        dogBreedsDao.deleteAll()
    }

    override suspend fun getDogBreed(id: Int): DogBreedsEntity {
        return dogBreedsDao.getDogBreed(id)
    }

    override suspend fun insertAllKeys(remoteKeys: List<RemoteKeys>) {
        remoteKeysDao.insertAll(remoteKeys)
    }

    override suspend fun remoteKeysBreedId(breedId: Int): RemoteKeys? {
        return remoteKeysDao.remoteKeysBreedId(breedId)
    }

    override suspend fun clearRemoteKeys() {
        remoteKeysDao.clearRemoteKeys()
    }

    override suspend fun performTransaction(transaction: suspend ()->Unit) {
        database.withTransaction {
            transaction()
        }
    }
}