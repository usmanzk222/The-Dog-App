package com.usman.mvvmsample.di.module

import com.usman.mvvmsample.data.local.DogBreedsLocalDataSource
import com.usman.mvvmsample.data.local.IDogBreedsLocalDataSource
import com.usman.mvvmsample.data.remote.DogBreedsRemoteDataSource
import com.usman.mvvmsample.data.remote.IDogBreedsRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {

    @Binds
    abstract fun provideBreedsRemoteDataSource(dataSource: DogBreedsRemoteDataSource): IDogBreedsRemoteDataSource

    @Binds
    abstract fun provideBreedsLocalDataSource(dataSource: DogBreedsLocalDataSource): IDogBreedsLocalDataSource
}