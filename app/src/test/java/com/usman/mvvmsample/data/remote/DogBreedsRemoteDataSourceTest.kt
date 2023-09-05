package com.usman.mvvmsample.data.remote

import com.usman.mvvmsample.testhelper.DogBreedEntityFactory
import com.usman.mvvmsample.util.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertFailsWith

class DogBreedsRemoteDataSourceTest {

    private lateinit var subject: DogBreedsRemoteDataSource

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var serviceGateway: ServiceGateway

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        subject = DogBreedsRemoteDataSource(serviceGateway)
    }

    @Test
    fun getDogBreeds_serviceGatewayGetBreedsList_returnsBreedsSuccessfully() = runTest {
        val expected = (0 .. 10).map(DogBreedEntityFactory::createNetworkModel)
        coEvery { serviceGateway.getDogBreeds(any(),any()) } returns expected

        assertEquals(
            expected,
            subject.getDogBreeds(1,10)
        )
    }

    @Test
    fun getDogBreeds_serviceGatewayGetBreedsList_throwsException() = runTest {

        val throwable = Throwable("Network Error")
        coEvery { serviceGateway.getDogBreeds(any(),any()) } throws throwable

        assertFailsWith<Throwable> {
            subject.getDogBreeds(1,20)
        }
    }

}