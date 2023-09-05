package com.usman.mvvmsample.data.local.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.usman.mvvmsample.data.model.Measurement

@Entity(tableName = "DogBreeds")
data class DogBreedsEntity(
    @PrimaryKey
    val id: Int,
    val bredFor: String?,
    val breedGroup: String?,
    @Embedded(prefix = "height_")
    val height: Measurement?,
    val lifeSpan: String?,
    val name: String ?,
    val origin: String?,
    val referenceImageId: String?,
    val temperament: String?,
    @Embedded
    val weight: Measurement?,
    val description: String?
)