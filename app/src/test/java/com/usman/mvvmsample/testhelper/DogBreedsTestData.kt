package com.usman.mvvmsample.testhelper

import com.usman.mvvmsample.data.local.db.entities.DogBreedsEntity
import com.usman.mvvmsample.data.mapToEntity
import com.usman.mvvmsample.data.model.Measurement
import com.usman.mvvmsample.data.model.NetworkDogBreed

object DogBreedEntityFactory{

    fun createEntity(id:Int): DogBreedsEntity {
        return createNetworkModel(id).mapToEntity()
    }

    fun createNetworkModel(id:Int): NetworkDogBreed{
        return NetworkDogBreed(
            id = id,
            name = "Dog Name $id",
            bredFor = "Bred For $id",
            breedGroup = "Breed Group $id",
            lifeSpan = "Life Span $id",
            temperament = "Temperament $id",
            origin = "Origin $id",
            referenceImageId = "Image Reference $id",
            weight = Measurement(
                imperial = "Imperial $id",
                metric = "Metric $id"
            ),
            height = Measurement(
                imperial = "Imperial $id",
                metric = "Metric $id"
            ),
            description = "Description $id"
        )
    }
}