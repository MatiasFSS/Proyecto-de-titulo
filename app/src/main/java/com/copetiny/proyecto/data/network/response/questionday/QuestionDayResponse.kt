package com.copetiny.proyecto.data.network.response.questionday

import com.google.gson.annotations.SerializedName

data class QuestionDayResponse (
    @SerializedName("pregunta") val pregunta:String,
    @SerializedName("alternativaA") val alternativaA:String,
    @SerializedName("alternativaB") val alternativaB:String,
    @SerializedName("alternativaC") val alternativaC:String,
    @SerializedName("alternativaD") val alternativaD:String,
    @SerializedName("respuesta") val respuesta:String
)