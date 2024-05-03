package com.copetiny.proyecto.ui.detailquiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.copetiny.proyecto.data.network.response.quiz.QuizResponse
import com.copetiny.proyecto.domain.model.quiz.QuestionModel
import com.copetiny.proyecto.domain.model.quiz.QuizModel
import com.copetiny.proyecto.domain.usecase.GetQuizUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class QuizDetailViewModel @Inject constructor(private val getQuizUseCase: GetQuizUseCase):ViewModel() {
    private var _state = MutableStateFlow<QuizDetailState>(QuizDetailState.Loading)
    val state: StateFlow<QuizDetailState> = _state

    lateinit var quiz: QuizModel

    private var listaPreguntas = mutableListOf<QuizResponse>()
    var preguntasMostradas = 0


    fun getQuiz(id: QuizModel) {
        quiz = id
        viewModelScope.launch {
            _state.value = QuizDetailState.Loading
            val result = withContext(Dispatchers.IO){
                getQuizUseCase(id.name)
            }

            Log.i("mama2", result.toString())

            if (result.isNotEmpty()) {
                listaPreguntas.addAll(result)
                mostrarSiguientePregunta()
            } else {
                _state.value = QuizDetailState.Error("Ha ocurrido un error, intente más tarde")
            }
        }
    }

    private fun mostrarPregunta() {
        if (listaPreguntas.isNotEmpty()) {
            val indice = (0 until listaPreguntas.size).random()
            val random = listaPreguntas[indice]
            _state.value = QuizDetailState.Success(
                random.pregunta,
                random.alternativaA,
                random.alternativaB,
                random.alternativaC,
                random.alternativaD,
                random.respuesta,
                random.dificultad,
                quiz
            )
            listaPreguntas.removeAt(indice)
        } else {
            _state.value = QuizDetailState.Error("El quiz ha terminado")
        }
    }

    fun mostrarSiguientePregunta() {
        if (preguntasMostradas < 5) {
            mostrarPregunta()
            preguntasMostradas++
        } else {
            _state.value = QuizDetailState.Error("Ya se han mostrado todas las preguntas")
        }
    }
}
