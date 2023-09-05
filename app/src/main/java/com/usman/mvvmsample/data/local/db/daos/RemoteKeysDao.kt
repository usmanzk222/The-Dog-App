package com.usman.mvvmsample.data.local.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.usman.mvvmsample.data.local.db.entities.RemoteKeys


@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE breedId = :breedId")
    suspend fun remoteKeysBreedId(breedId: Int): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}