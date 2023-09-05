package com.usman.mvvmsample.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import androidx.paging.testing.asSnapshot
import com.usman.mvvmsample.data.repository.DogBreedsRemoteMediator
import com.usman.mvvmsample.data.toDomainModel
import com.usman.mvvmsample.domain.usecase.GetDogBreedsUseCase
import com.usman.mvvmsample.testhelper.DogBreedEntityFactory
import com.usman.mvvmsample.testhelper.FakeLocalDataSource
import com.usman.mvvmsample.util.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
class GetDogBreedsUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    lateinit var subject: GetDogBreedsUseCase
    private val localDataSource = FakeLocalDataSource()

    @MockK
    private lateinit var mediator: DogBreedsRemoteMediator


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        subject = GetDogBreedsUseCase(localDataSource, mediator)
    }

    @Test
    fun getDogBreeds_whenDataAvailable_returnsDomainModelList() = runTest {

        val list = (0..10).map {
            DogBreedEntityFactory.createEntity(it)
        }
        localDataSource.insertAllDogBreeds(list)

        val result: RemoteMediator.MediatorResult = RemoteMediator.MediatorResult.Success(true)
        coEvery { mediator.load(any(), any()) } returns result

        val data = subject.getDogBreeds(10).asSnapshot()
        assertEquals(data.first(),list.first().toDomainModel())
    }

    @Test
    fun getDogBreeds_whenNoDataAvailable_returnsEmptyList() = runTest {

        val result: RemoteMediator.MediatorResult = RemoteMediator.MediatorResult.Success(true)
        coEvery { mediator.load(any(), any()) } returns result

        val data = subject.getDogBreeds(10).asSnapshot()
        assert(data.isEmpty())
    }

    @Test
    fun getDogBreeds_whenError_throwsException() = runTest {

        val expected = Throwable("Exception")
        val result: RemoteMediator.MediatorResult = RemoteMediator.MediatorResult.Error(expected)
        coEvery { mediator.load(any(), any()) } returns result


        assertFailsWith<Throwable> {
            subject.getDogBreeds(10).asSnapshot()
        }
    }
}
