package com.copetiny.proyecto.ui.questionday

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.copetiny.proyecto.data.network.response.questionday.QuestionDayResponse
import com.copetiny.proyecto.domain.usecase.GetQuestionDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class QuestionDayViewModel @Inject constructor(private val getQuestionDayUseCase: GetQuestionDayUseCase) : ViewModel() {
    private var _state = MutableStateFlow<QuestionDayState>(QuestionDayState.Loading)
    val state: StateFlow<QuestionDayState> = _state

    private var listaPreguntas = mutableListOf<QuestionDayResponse>()
    var currentPregunta = 0

    init {
        Log.d("QuestionDayViewModel", "ViewModel initialized")
        getQuestionDay()
    }

    fun getQuestionDay() {
        viewModelScope.launch {
            _state.value = QuestionDayState.Loading
            val result = withContext(Dispatchers.IO) {
                Log.i("QuestionDayViewModel", "Fetching question of the day")
                getQuestionDayUseCase()
            }
            Log.i("QuestionDayViewModel", "Result: $result")

            if (result.isNotEmpty()) {
                listaPreguntas.addAll(result)
                mostrarPregunta()
            } else {
                _state.value = QuestionDayState.Error("Ha ocurrido un error, intente más tarde")
            }
        }
    }

   fun mostrarPregunta() {
        if (listaPreguntas.isNotEmpty()) {
            val indice = (0 until listaPreguntas.size).random()
            val random = listaPreguntas[indice]
            _state.value = QuestionDayState.Success(
                random.pregunta,
                random.alternativaA,
                random.alternativaB,
                random.alternativaC,
                random.alternativaD,
                random.respuesta
            )
            listaPreguntas.removeAt(indice)
        } else {
            _state.value = QuestionDayState.Error("El quiz ha terminado")
        }
    }
}