package com.copetiny.proyecto.data.network

import com.copetiny.proyecto.data.network.response.encyclopedia.MaterialResponse
import com.copetiny.proyecto.data.network.response.questionday.QuestionDayResponse
import com.copetiny.proyecto.data.network.response.quiz.QuizResponse

import com.copetiny.proyecto.data.network.response.tutorials.TutorialsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProyectoApiService {

    @GET("/enciclopedia/nombre_material/{nombre_material}")
    suspend fun getEncyclopedia(@Path("nombre_material") id:String): MaterialResponse

    @GET("/tutoriales/nombre_tutorial/{nombre_tutorial}")
    suspend fun getTutorials(@Path("nombre_tutorial") id:String):TutorialsResponse

    @GET("/trivias/dificultad/{dificultad}")
    suspend fun getQuiz(@Path("dificultad") id:String):List<QuizResponse>

    @GET("/pregunta")
    suspend fun getQuestionDay():List<QuestionDayResponse>

}