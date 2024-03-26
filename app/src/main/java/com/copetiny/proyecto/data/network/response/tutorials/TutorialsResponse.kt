package com.copetiny.proyecto.data.network.response.tutorials

import com.copetiny.proyecto.domain.model.encyclopedia.MaterialModel
import com.copetiny.proyecto.domain.model.tutorials.TutorialModel
import com.copetiny.proyecto.domain.model.tutorials.TutorialsModel
import com.google.gson.annotations.SerializedName

data class TutorialsResponse(
    @SerializedName("nombre_tutorial") val nombre_tutorial:String,
    @SerializedName("descripcion_tutorial") val descripcion_tutorial:String,
    @SerializedName("informacion_tutorial")  val informacion_tutorial:String
){
    fun toDomainTutorials(): TutorialModel {
        return TutorialModel(
            nombre_tutorial = nombre_tutorial,
            descripcion_tutorial = descripcion_tutorial,
            informacion_tutorial = informacion_tutorial
        )
    }
}