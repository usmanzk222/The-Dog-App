package com.usman.mvvmsample.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.usman.mvvmsample.data.remote.ServiceGateway
import com.usman.mvvmsample.di.qualifiers.DogsAppRetrofitClient
import com.usman.mvvmsample.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Named("BASE_URL")
    @Provides
    fun getBaseUrl(): String {
        return Constants.BASE_URL
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun getOkHttpClient(): OkHttpClient {
        val interceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @DogsAppRetrofitClient
    @Provides
    @Singleton
    fun provideRetroFit(
        gson: Gson,
        @Named("BASE_URL") baseUrl: String,
        httpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideGateway(@DogsAppRetrofitClient retrofit: Retrofit): ServiceGateway {
        return retrofit.create(ServiceGateway::class.java)
    }

}
