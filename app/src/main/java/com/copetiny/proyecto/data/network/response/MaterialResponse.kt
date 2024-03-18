package com.copetiny.proyecto.data.network.response

import com.copetiny.proyecto.domain.model.MaterialModel
import com.google.gson.annotations.SerializedName

data class MaterialResponse(
    @SerializedName("nombre_material") val nombre_material:String,
    @SerializedName("descripcion") val descripcion:String,
    @SerializedName("informacion") val informacion:String
){
    fun toDomain():MaterialModel{
        return MaterialModel(
            nombre_material = nombre_material,
            descripcion = descripcion,
            informacion = informacion
            )
    }
}