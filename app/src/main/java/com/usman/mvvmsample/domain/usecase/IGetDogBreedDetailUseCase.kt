package com.usman.mvvmsample.domain.usecase

import com.usman.mvvmsample.domain.model.DogBreed
import kotlinx.coroutines.flow.Flow

interface IGetDogBreedDetailUseCase {
    fun getDogBreedDetail(id: Int): Flow<DogBreed>
}