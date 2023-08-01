package com.example.movieappdicoding

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie (
    @SerializedName("Title")
    val title: String,
    @SerializedName("Released")
    val released: String,
    @SerializedName("Director")
    val director: String,
    @SerializedName("Genre")
    val genre: String,
    @SerializedName("Plot")
    val plot: String,
    @SerializedName("Images")
    val images: List<String>,
) : Parcelable
