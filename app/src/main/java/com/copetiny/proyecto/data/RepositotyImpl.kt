package com.copetiny.proyecto.data

import android.util.Log
import com.copetiny.proyecto.data.network.ProyectoApiService
import com.copetiny.proyecto.data.network.response.questionday.QuestionDayResponse
import com.copetiny.proyecto.data.network.response.quiz.QuizResponse
import com.copetiny.proyecto.domain.Repository
import com.copetiny.proyecto.domain.model.encyclopedia.MaterialModel
import com.copetiny.proyecto.domain.model.quiz.QuestionModel
import com.copetiny.proyecto.domain.model.tutorials.TutorialModel
import com.copetiny.proyecto.ui.questionday.QuestionDay
import javax.inject.Inject

class RepositotyImpl @Inject constructor(private val apiService: ProyectoApiService) :Repository {
    override suspend fun getEncyclopedia(id:String): MaterialModel?
    {
        runCatching { apiService.getEncyclopedia(id) }
            .onSuccess { return  it.toDomain()}
            .onFailure { Log.i("Matias", "Ha ocurrido un error ${it.message}") }
        return null
    }

    override suspend fun getTutorials(id:String):TutorialModel?
    {
        runCatching { apiService.getTutorials(id) }
            .onSuccess { return it.toDomainTutorials() }
            .onFailure { Log.i("Matias", "Ha ocurrido un error ${it.message}") }
        return null
    }

    override suspend fun getQuiz(id:String): List<QuizResponse>
    {
       runCatching { apiService.getQuiz(id) }
           .onSuccess { return it }
           .onFailure { Log.i("Matias", "Ha ocurrido un error ${it.message}")}
        return emptyList()
    }

    override suspend fun getQuestionDay():List<QuestionDayResponse>
    {
        runCatching { apiService.getQuestionDay()}
            .onSuccess { return it }
            .onFailure {  Log.i("Matias", "Ha ocurrido un error ${it.message}")}
        return emptyList()
    }
}