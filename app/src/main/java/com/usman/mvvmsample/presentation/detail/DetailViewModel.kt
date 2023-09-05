package com.usman.mvvmsample.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usman.mvvmsample.domain.model.DogBreed
import com.usman.mvvmsample.domain.usecase.IGetDogBreedDetailUseCase
import com.usman.mvvmsample.presentation.utils.Result
import com.usman.mvvmsample.presentation.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    useCase: IGetDogBreedDetailUseCase
) : ViewModel() {

    private val breedId: Int = checkNotNull(savedStateHandle["breedId"])

    val breedDetail: StateFlow<DetailUiState> = useCase.getDogBreedDetail(breedId)
        .asResult().map { result ->
            when (result) {
                is Result.Success -> {
                    val data = result.data
                    DetailUiState.Success(data)
                }

                is Result.Loading -> {
                    DetailUiState.Loading
                }

                is Result.Error -> {
                    DetailUiState.Error(result.exception?.message)
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            DetailUiState.Loading
        )

}

sealed interface DetailUiState {
    data class Success(val breedDetail: DogBreed) : DetailUiState
    data class Error(val message: String?) : DetailUiState
    object Loading : DetailUiState
}