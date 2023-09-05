package com.usman.mvvmsample.testdata

import com.usman.mvvmsample.data.model.Measurement
import com.usman.mvvmsample.data.model.NetworkDogBreed

object DogBreedFactory{

    fun produceModel(id:Int): NetworkDogBreed {
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