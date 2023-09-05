package com.usman.mvvmsample.domain.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.usman.mvvmsample.data.local.IDogBreedsLocalDataSource
import com.usman.mvvmsample.data.repository.DogBreedsRemoteMediator
import com.usman.mvvmsample.data.toDomainModel
import com.usman.mvvmsample.domain.model.DogBreedMinimal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDogBreedsUseCase @Inject constructor(private val localDataSource: IDogBreedsLocalDataSource,
                                              private val mediator: DogBreedsRemoteMediator
) :
    IGetDogBreedsUseCase {
    @OptIn(ExperimentalPagingApi::class)
    override fun getDogBreeds(limit: Int): Flow<PagingData<DogBreedMinimal>> {
        return Pager(
            config = PagingConfig(pageSize = limit),
            remoteMediator = mediator,
            pagingSourceFactory = { localDataSource.getDogBreeds() }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toDomainModel()
            }
        }
    }
}
