package com.usman.mvvmsample.domain.usecase

import androidx.paging.PagingData
import com.usman.mvvmsample.domain.model.DogBreedMinimal
import kotlinx.coroutines.flow.Flow

interface IGetDogBreedsUseCase {
    fun getDogBreeds(limit: Int): Flow<PagingData<DogBreedMinimal>>
}