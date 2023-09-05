package com.usman.mvvmsample.domain.usecase

import com.usman.mvvmsample.data.repository.DogBreedsRepository
import com.usman.mvvmsample.domain.model.DogBreed
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDogBreedDetailUseCase @Inject constructor(private val repository: DogBreedsRepository) :
    IGetDogBreedDetailUseCase {

    override fun getDogBreedDetail(id: Int): Flow<DogBreed> = repository.getDogBreed(id)
}