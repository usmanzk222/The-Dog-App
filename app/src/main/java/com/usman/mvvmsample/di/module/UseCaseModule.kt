package com.usman.mvvmsample.di.module

import com.usman.mvvmsample.domain.usecase.GetDogBreedDetailUseCase
import com.usman.mvvmsample.domain.usecase.GetDogBreedsUseCase
import com.usman.mvvmsample.domain.usecase.IGetDogBreedDetailUseCase
import com.usman.mvvmsample.domain.usecase.IGetDogBreedsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {

    @Binds
    abstract fun provideDogBreedsUseCase(useCase: GetDogBreedsUseCase): IGetDogBreedsUseCase

    @Binds
    abstract fun provideDogBreedDetailUseCase(useCase: GetDogBreedDetailUseCase): IGetDogBreedDetailUseCase
}