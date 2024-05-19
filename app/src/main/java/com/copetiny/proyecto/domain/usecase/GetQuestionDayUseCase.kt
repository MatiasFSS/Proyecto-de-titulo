package com.copetiny.proyecto.domain.usecase

import com.copetiny.proyecto.data.network.response.questionday.QuestionDayResponse
import com.copetiny.proyecto.domain.Repository
import javax.inject.Inject

class GetQuestionDayUseCase @Inject constructor(private val repository:Repository) {
    suspend operator fun invoke():List<QuestionDayResponse>{
        return repository.getQuestionDay()
    }
}