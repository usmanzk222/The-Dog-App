package com.usman.mvvmsample.data.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val breedId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)