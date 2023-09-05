package com.usman.mvvmsample.di.module

import android.content.Context
import androidx.room.Room
import com.usman.mvvmsample.data.local.db.DogAppDatabase
import com.usman.mvvmsample.data.local.db.daos.DogBreedsDao
import com.usman.mvvmsample.data.local.db.daos.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Named("IO")
    fun provideIODispatchers(): CoroutineDispatcher {
        return Dispatchers.IO
    }


    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): DogAppDatabase {
        return Room.databaseBuilder(context, DogAppDatabase::class.java, "mvvm-sample-db").build()

    }

    @Singleton
    @Provides
    fun provideDogBreedsDao(database: DogAppDatabase): DogBreedsDao {
        return database.dogBreedsDAO()
    }

    @Singleton
    @Provides
    fun provideRemoteKeysDao(database: DogAppDatabase): RemoteKeysDao {
        return database.remoteKeysDao()
    }

}
