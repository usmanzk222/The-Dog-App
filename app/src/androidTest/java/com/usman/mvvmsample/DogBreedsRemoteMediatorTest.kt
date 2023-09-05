package com.usman.mvvmsample

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.usman.mvvmsample.data.local.DogBreedsLocalDataSource
import com.usman.mvvmsample.data.local.db.DogAppDatabase
import com.usman.mvvmsample.data.local.db.entities.DogBreedsEntity
import com.usman.mvvmsample.data.mapToEntity
import com.usman.mvvmsample.data.repository.DogBreedsRemoteMediator
import com.usman.mvvmsample.testdata.DogBreedFactory
import com.usman.mvvmsample.testdata.FakeRemoteSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class DogBreedsRemoteMediatorTest {

    private val remoteDataSource = FakeRemoteSource()

    private lateinit var localDataSource: DogBreedsLocalDataSource

    private lateinit var remoteMediator: DogBreedsRemoteMediator

    private val mockDb = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(), DogAppDatabase::class.java
    ).build()

    private val fakeData = (1 ..20).map {
        DogBreedFactory.produceModel(it)
    }

    @Before
    fun setUp() {

        localDataSource =
            DogBreedsLocalDataSource(mockDb.dogBreedsDAO(), mockDb.remoteKeysDao(), mockDb)

        remoteMediator = DogBreedsRemoteMediator(
            remoteDataSource,
            localDataSource
        )
    }

    @After
    fun tearDown() {
        remoteDataSource.clearDogBreeds()
        mockDb.clearAllTables()
    }

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        fakeData.map {
            remoteDataSource.addDogBreed(it)
        }

        val pagingState = PagingState<Int, DogBreedsEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue { result is RemoteMediator.MediatorResult.Success }
        assertFalse { (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
    }


    @Test
    fun refreshLoad_WhenNoDataIsPresent_ReturnsSuccessResult() = runTest {
        val pagingState = PagingState<Int, DogBreedsEntity>(
            listOf(),
            null,
            PagingConfig(10),
            5
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue { result is RemoteMediator.MediatorResult.Success }
        assertTrue { (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
    }

    @Test
    fun appendLoad_WhenNoDataIsPresent_ReturnsSuccessResult() = runTest {
        fakeData.map {
            remoteDataSource.addDogBreed(it)
        }

        val pagingState = PagingState<Int, DogBreedsEntity>(
            listOf(),
            null,
            PagingConfig(25),
            10
        )
        remoteMediator.load(LoadType.REFRESH, pagingState)

        val pages :List<PagingSource.LoadResult.Page<Int, DogBreedsEntity>> = listOf(
            PagingSource.LoadResult.Page(
                fakeData.map { it.mapToEntity() },0,null,
            )
        )

        val appendPagingState = PagingState<Int, DogBreedsEntity>(
            pages,
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.APPEND, appendPagingState)
        assertTrue { result is RemoteMediator.MediatorResult.Success }
        assertTrue { (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
    }

    @Test
    fun appendLoad_WhenDataIsPresent_ReturnsSuccessResult() = runTest {

        fakeData.map {
            remoteDataSource.addDogBreed(it)
        }

        val pagingState = PagingState<Int, DogBreedsEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        remoteMediator.load(LoadType.REFRESH, pagingState)

        val pages :List<PagingSource.LoadResult.Page<Int, DogBreedsEntity>> = listOf(
            PagingSource.LoadResult.Page(
                fakeData.subList(0,10).map { it.mapToEntity() },0,1,
            )
        )

        val appendPagingState = PagingState<Int, DogBreedsEntity>(
            pages,
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.APPEND, appendPagingState)
        assertTrue { result is RemoteMediator.MediatorResult.Success }
        assertFalse { (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
    }


    @Test
    fun refreshLoad_ErrorOccurs_ReturnsErrorResult() = runTest {
        remoteDataSource.failureMsg = "Throw test failure"

        val pagingState = PagingState<Int, DogBreedsEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue {result is RemoteMediator.MediatorResult.Error }
    }
}

