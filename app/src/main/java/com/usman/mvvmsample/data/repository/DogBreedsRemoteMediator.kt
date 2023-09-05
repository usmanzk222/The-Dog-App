package com.usman.mvvmsample.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.usman.mvvmsample.data.local.IDogBreedsLocalDataSource
import com.usman.mvvmsample.data.local.db.entities.DogBreedsEntity
import com.usman.mvvmsample.data.local.db.entities.RemoteKeys
import com.usman.mvvmsample.data.mapToEntity
import com.usman.mvvmsample.data.remote.IDogBreedsRemoteDataSource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class DogBreedsRemoteMediator @Inject constructor(
    private val remoteDataSource: IDogBreedsRemoteDataSource,
    private val localDataSource: IDogBreedsLocalDataSource
) : RemoteMediator<Int, DogBreedsEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DogBreedsEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 0

            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with endOfPaginationReached = false because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }


        try {
            val dogBreeds = remoteDataSource.getDogBreeds(page, state.config.pageSize).map {
                it.mapToEntity()
            }


            val endOfPaginationReached = dogBreeds.isEmpty() || dogBreeds.size < state.config.pageSize
            localDataSource.performTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    localDataSource.clearRemoteKeys()
                    localDataSource.deleteAllDogBreeds()
                }
                val prevKey = if (page == 0) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = dogBreeds.map {
                    RemoteKeys(breedId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                localDataSource.insertAllKeys(keys)
                localDataSource.insertAllDogBreeds(dogBreeds)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, DogBreedsEntity>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item

        return state.lastItemOrNull()
            ?.let { breed ->
                // Get the remote keys of the last item retrieved
                localDataSource.remoteKeysBreedId(breed.id)
            }
    }
}