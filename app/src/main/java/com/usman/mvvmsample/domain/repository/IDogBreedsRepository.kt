package com.usman.mvvmsample.domain.repository

import com.usman.mvvmsample.domain.model.DogBreed
import kotlinx.coroutines.flow.Flow

interface IDogBreedsRepository{
    fun getDogBreed(id: Int): Flow<DogBreed>
}