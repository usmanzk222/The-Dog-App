package com.usman.mvvmsample.testhelper

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSourceFactory
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.map
import androidx.paging.testing.asPagingSourceFactory
import com.usman.mvvmsample.data.local.db.entities.DogBreedsEntity
import com.usman.mvvmsample.data.toDomainModel
import com.usman.mvvmsample.domain.model.DogBreedMinimal
import com.usman.mvvmsample.domain.usecase.IGetDogBreedsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class FakeDogBreedsUseCase : IGetDogBreedsUseCase {

    private val localDataSource = FakePagingSource()

    var result: RemoteMediator.MediatorResult = RemoteMediator.MediatorResult.Success(true)

    override fun getDogBreeds(limit: Int): Flow<PagingData<DogBreedMinimal>> {

        return Pager(
            config = PagingConfig(pageSize = limit),
            remoteMediator = FakeMediator(result),
            pagingSourceFactory = { localDataSource.pagingSource }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toDomainModel()
            }
        }
    }

}

class FakePagingSource {
    private val items = (0..100).map {
        DogBreedEntityFactory.createEntity(it)
    }

    private val pagingSourceFactory: PagingSourceFactory<Int, DogBreedsEntity> =
        items.asPagingSourceFactory()

    val pagingSource = pagingSourceFactory()
}

@OptIn(ExperimentalPagingApi::class)
class FakeMediator(private val result: MediatorResult) : RemoteMediator<Int, DogBreedsEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DogBreedsEntity>
    ): MediatorResult {
        return result
    }

}