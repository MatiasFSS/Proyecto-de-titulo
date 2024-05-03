package com.copetiny.proyecto.domain.usecase

import com.copetiny.proyecto.data.network.response.quiz.QuizResponse
import com.copetiny.proyecto.domain.Repository
import com.copetiny.proyecto.domain.model.quiz.QuestionModel
import javax.inject.Inject

class GetQuizUseCase  @Inject constructor(private val repository:Repository){
    suspend operator fun invoke(id:String): List<QuizResponse> {
        return repository.getQuiz(id)
    }
}