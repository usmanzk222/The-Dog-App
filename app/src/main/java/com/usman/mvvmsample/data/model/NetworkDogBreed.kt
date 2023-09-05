package com.usman.mvvmsample.data.model

import com.google.gson.annotations.SerializedName

data class NetworkDogBreed(
    @SerializedName("id")
    val id: Int,
    @SerializedName("bred_for")
    val bredFor: String? = "",
    @SerializedName("breed_group")
    val breedGroup: String? = "",
    @SerializedName("height")
    val height: Measurement? = Measurement(),
    @SerializedName("life_span")
    val lifeSpan: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("origin")
    val origin: String? = "",
    @SerializedName("reference_image_id")
    val referenceImageId: String? = "",
    @SerializedName("temperament")
    val temperament: String? = "",
    @SerializedName("weight")
    val weight: Measurement? = Measurement(),
    @SerializedName("description")
    val description: String? = ""
)