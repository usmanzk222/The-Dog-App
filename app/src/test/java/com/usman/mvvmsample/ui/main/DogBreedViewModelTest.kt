package com.usman.mvvmsample.ui.main

import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import androidx.paging.testing.asSnapshot
import com.usman.mvvmsample.data.toDomainModel
import com.usman.mvvmsample.domain.model.DogBreedMinimal
import com.usman.mvvmsample.presentation.main.DogBreedViewModel
import com.usman.mvvmsample.testhelper.DogBreedEntityFactory
import com.usman.mvvmsample.testhelper.FakeDogBreedsUseCase
import com.usman.mvvmsample.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class DogBreedViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: DogBreedViewModel

    private val useCase = FakeDogBreedsUseCase()

    @Before
    fun setUp() {
        viewModel = DogBreedViewModel(useCase)
    }

    @Test
    fun albumsUiStateState_useCaseReturnsAllData_IsSuccess() = runTest {

        val result = mutableListOf<DogBreedMinimal>()
        val dat = useCase.getDogBreeds(10).asSnapshot()
        result.addAll(dat)

        assertEquals(DogBreedEntityFactory.createEntity(0).toDomainModel(), result.first())
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun albumsUiStateState_useCaseReturnsError_throwsException() = runTest {
        val expected = Throwable("Exception")
        val mediatorResult: RemoteMediator.MediatorResult = RemoteMediator.MediatorResult.Error(expected)
        useCase.result = mediatorResult

        assertFailsWith<Throwable>{
            useCase.getDogBreeds(10).asSnapshot()
        }
    }
}
