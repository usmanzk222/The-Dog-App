package com.usman.mvvmsample.data.repository

import com.usman.mvvmsample.data.local.IDogBreedsLocalDataSource
import com.usman.mvvmsample.data.toDogBreed
import com.usman.mvvmsample.domain.model.DogBreed
import com.usman.mvvmsample.domain.repository.IDogBreedsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Named

class DogBreedsRepository @Inject constructor(
    private val localDataSource: IDogBreedsLocalDataSource,
    @Named("IO")
    private val dispatcher: CoroutineDispatcher
) : IDogBreedsRepository {

    override fun getDogBreed(id: Int): Flow<DogBreed> = flow{
         val dogBreed = localDataSource.getDogBreed(id).toDogBreed()
         emit(dogBreed)
     }.flowOn(dispatcher)


}

