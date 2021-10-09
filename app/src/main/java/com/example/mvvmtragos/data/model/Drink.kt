package com.example.mvvmtragos.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Drink(
    @SerializedName("idDrink")
    val id: String = "",
    @SerializedName("strDrinkThumb")
    val image: String = "",
    @SerializedName("strDrink")
    val name: String = "",
    @SerializedName("strInstructions")
    val description: String = "",
    @SerializedName("strAlcoholic")
    val hasAlcohol: String = "Non_Alcoholic"
): Parcelable

data class Drinks(
    val drinks: List<Drink> = listOf()
)

@Entity(tableName = "tragosEntity")
data class DrinkEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "trago_imagen")
    val image: String = "",
    @ColumnInfo(name = "trago_nombre")
    val name: String = "",
    @ColumnInfo(name = "trago_descripcion")
    val description: String = "",
    @ColumnInfo(name = "trago_has_alcohol")
    val hasAlcohol: String = "Non_Alcoholic"
)

fun Drink.asDrinkEntity(): DrinkEntity = DrinkEntity(
    this.id,
    this.image,
    this.name,
    this.description,
    this.hasAlcohol
)

fun List<DrinkEntity>.asDrinkList(): List<Drink> = this.map {
    Drink(it.id,
        it.image,
        it.name,
        it.description,
        it.hasAlcohol)
}