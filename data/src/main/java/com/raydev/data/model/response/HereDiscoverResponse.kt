package com.raydev.data.model.response

import com.google.gson.annotations.SerializedName

data class HereDiscoverResponse(
    @SerializedName("items")
    val items: List<Item?>?
) {
    data class Item(
        @SerializedName("access")
        val access: List<Acces?>?,
        @SerializedName("address")
        val address: Address?,
        @SerializedName("categories")
        val categories: List<Category?>?,
        @SerializedName("contacts")
        val contacts: List<Contact?>?,
        @SerializedName("distance")
        val distance: Int?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("language")
        val language: String?,
        @SerializedName("ontologyId")
        val ontologyId: String?,
        @SerializedName("position")
        val position: Position?,
        @SerializedName("resultType")
        val resultType: String?,
        @SerializedName("title")
        val title: String?
    ) {
        data class Acces(
            @SerializedName("lat")
            val lat: Double?,
            @SerializedName("lng")
            val lng: Double?
        )

        data class Address(
            @SerializedName("city")
            val city: String?,
            @SerializedName("countryCode")
            val countryCode: String?,
            @SerializedName("countryName")
            val countryName: String?,
            @SerializedName("county")
            val county: String?,
            @SerializedName("countyCode")
            val countyCode: String?,
            @SerializedName("district")
            val district: String?,
            @SerializedName("label")
            val label: String?,
            @SerializedName("postalCode")
            val postalCode: String?,
            @SerializedName("subdistrict")
            val subdistrict: String?
        )

        data class Category(
            @SerializedName("id")
            val id: String?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("primary")
            val primary: Boolean?
        )

        data class Contact(
            @SerializedName("phone")
            val phone: List<Phone?>?
        ) {
            data class Phone(
                @SerializedName("value")
                val value: String?
            )
        }

        data class Position(
            @SerializedName("lat")
            val lat: Double?,
            @SerializedName("lng")
            val lng: Double?
        )
    }
}
