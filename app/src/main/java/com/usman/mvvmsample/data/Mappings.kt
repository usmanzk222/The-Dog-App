package com.usman.mvvmsample.data

import com.usman.mvvmsample.data.local.db.entities.DogBreedsEntity
import com.usman.mvvmsample.data.model.Measurement
import com.usman.mvvmsample.data.model.NetworkDogBreed
import com.usman.mvvmsample.domain.model.DogBreed
import com.usman.mvvmsample.domain.model.DogBreedMinimal

fun DogBreedsEntity.toDogBreed(): DogBreed {

    return DogBreed(
        id = id,
        bredFor = bredFor ?: "",
        breedGroup = breedGroup ?: "",
        height = height ?: Measurement(),
        lifeSpan = lifeSpan ?: "",
        name = name ?: "",
        origin = origin ?: "",
        referenceImageId = referenceImageId ?: "",
        temperament = temperament ?: "",
        weight = weight ?: Measurement(),
        description = description ?: ""
    )
}

fun DogBreedsEntity.toDomainModel(): DogBreedMinimal {
    return DogBreedMinimal(
        id = id,
        imageReferenceId = referenceImageId ?: "",
        name = name ?: ""
    )
}

fun NetworkDogBreed.mapToEntity(): DogBreedsEntity {

    return DogBreedsEntity(
        id,
        bredFor = bredFor,
        breedGroup = breedGroup,
        height = height,
        lifeSpan = lifeSpan,
        name = name,
        origin = origin,
        referenceImageId = referenceImageId,
        temperament = temperament,
        weight = weight,
        description = description
    )
}



