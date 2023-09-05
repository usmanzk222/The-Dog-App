package com.usman.mvvmsample.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TestRetrofitClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DogsAppRetrofitClient
