package com.usman.mvvmsample.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.usman.mvvmsample.data.local.db.daos.DogBreedsDao
import com.usman.mvvmsample.data.local.db.daos.RemoteKeysDao
import com.usman.mvvmsample.data.local.db.entities.DogBreedsEntity
import com.usman.mvvmsample.data.local.db.entities.RemoteKeys

@Database(
    entities = [DogBreedsEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)

abstract class DogAppDatabase: RoomDatabase() {
    abstract fun dogBreedsDAO(): DogBreedsDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}