package com.usman.mvvmsample.data.local.db.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.usman.mvvmsample.data.local.db.entities.DogBreedsEntity

@Dao
interface DogBreedsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entity: List<DogBreedsEntity>)

    @Query("SELECT * FROM DogBreeds")
    fun getAllDogBreeds(): PagingSource<Int, DogBreedsEntity>

    @Query("SELECT * FROM DogBreeds WHERE id = :id")
    suspend fun getDogBreed(id: Int): DogBreedsEntity

    @Query("DELETE FROM DogBreeds")
    fun deleteAll()

}
