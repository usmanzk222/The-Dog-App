package com.usman.mvvmsample.di.module

import com.usman.mvvmsample.data.repository.DogBreedsRepository
import com.usman.mvvmsample.domain.repository.IDogBreedsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideMainRepository(repository: DogBreedsRepository):IDogBreedsRepository
}