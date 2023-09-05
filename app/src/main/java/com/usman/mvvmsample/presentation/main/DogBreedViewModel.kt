package com.usman.mvvmsample.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.usman.mvvmsample.domain.model.DogBreedMinimal
import com.usman.mvvmsample.domain.usecase.IGetDogBreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DogBreedViewModel @Inject constructor(
    pagedUseCase: IGetDogBreedsUseCase
) : ViewModel() {

    val dogBreeds: Flow<PagingData<DogBreedMinimal>> =
        pagedUseCase.getDogBreeds(20)
            .cachedIn(viewModelScope)
}