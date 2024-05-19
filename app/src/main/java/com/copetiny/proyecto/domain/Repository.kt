package com.copetiny.proyecto.domain

import com.copetiny.proyecto.data.network.response.questionday.QuestionDayResponse
import com.copetiny.proyecto.data.network.response.quiz.QuizResponse
import com.copetiny.proyecto.domain.model.encyclopedia.MaterialModel
import com.copetiny.proyecto.domain.model.quiz.QuestionModel
import com.copetiny.proyecto.domain.model.tutorials.TutorialModel

interface Repository {
    suspend fun getEncyclopedia(id:String): MaterialModel?
    suspend fun getTutorials(id: String): TutorialModel?
    suspend fun getQuiz(id: String): List<QuizResponse>

    suspend fun getQuestionDay(): List<QuestionDayResponse>
}