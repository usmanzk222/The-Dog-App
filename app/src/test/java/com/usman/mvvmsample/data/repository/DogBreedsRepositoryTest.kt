package com.usman.mvvmsample.data.repository

import app.cash.turbine.test
import com.usman.mvvmsample.data.toDogBreed
import com.usman.mvvmsample.testhelper.DogBreedEntityFactory
import com.usman.mvvmsample.testhelper.FakeLocalDataSource
import com.usman.mvvmsample.util.MainDispatcherRule
import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class DogBreedsRepositoryTest {

    private lateinit var subject: DogBreedsRepository

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var localDataSource = FakeLocalDataSource()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        subject = DogBreedsRepository(localDataSource, mainDispatcherRule.dispatcher)
    }

    @Test
    fun getAlbums_dataSourceReturnsDogBreedEntity_returnsDogBreed() = runTest {

        val list = (0..10).map {
            DogBreedEntityFactory.createEntity(it)
        }
        localDataSource.insertAllDogBreeds(list)

        subject.getDogBreed(0).test {
            assertEquals(
                list.first().toDogBreed(),
                awaitItem()
            )
            awaitComplete()
        }
    }

    @Test
    fun getAlbums_dataSourceReturnsLastDogBreedEntity_returnsDogBreed() = runTest {

        val list = (0..10).map {
            DogBreedEntityFactory.createEntity(it)
        }
        localDataSource.insertAllDogBreeds(list)

        subject.getDogBreed(10).test {
            assertEquals(
                list.last().toDogBreed(),
                awaitItem()
            )
            awaitComplete()
        }
    }

    @Test
    fun getAlbums_dataSourceEmpty_throwsException() = runTest {

        subject.getDogBreed(0).test {
            awaitError()
        }
    }

}