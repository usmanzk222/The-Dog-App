package com.usman.mvvmsample.domain.model

import com.usman.mvvmsample.data.model.Measurement

data class DogBreed(
    val id: Int,
    val bredFor: String,
    val breedGroup: String,
    val height: Measurement,
    val lifeSpan: String,
    val name: String,
    val origin: String,
    val temperament: String,
    val weight: Measurement,
    val description: String,
    val referenceImageId: String,
)