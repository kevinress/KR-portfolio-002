package com.ress.usstatesdata

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

//      "ID State": "04000US01",
//      "State": "Alabama",
//      "ID Year": 2022,
//      "Year": "2022",
//      "Population": 5028092,
//      "Slug State": "alabama"

@Parcelize
data class Data(
    @SerializedName("State") val state: String,
    @SerializedName("Population") val population: String,
    @SerializedName("Slug State") val slugState: String
    ) : Parcelable

data class DatasResponse(val data: List<Data>)

data class PixabayResponse(val hits: List<Hit>)
data class Hit(val previewURL: String)