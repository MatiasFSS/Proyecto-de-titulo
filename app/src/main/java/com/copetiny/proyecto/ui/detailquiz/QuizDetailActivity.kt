package com.copetiny.proyecto.ui.detailquiz

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import com.copetiny.proyecto.R
import com.copetiny.proyecto.databinding.ActivityQuizDetailBinding
import com.copetiny.proyecto.domain.model.quiz.QuizModel
import com.copetiny.proyecto.ui.profile.SharedViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONObject

@AndroidEntryPoint
class QuizDetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityQuizDetailBinding
    private val quizDetailViewModel by viewModels<QuizDetailViewModel>()
    private val args:QuizDetailActivityArgs by navArgs()
    private val sharedViewModel: SharedViewModel by viewModels()
    var contadorPuntos = 0
    var contadorPreguntas = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        quizDetailViewModel.getQuiz(args.type)
        initUI()
    }

    private fun initUI(){
        initUIState()
        ivBack()
    }

    private fun initUIState(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                quizDetailViewModel.state.collect(){
                    Log.i("mama", quizDetailViewModel.state.value.toString())
                    when(it){
                        is QuizDetailState.Error -> Error()
                        QuizDetailState.Loading -> Loading()
                        is QuizDetailState.Success -> {Success(it)}
                    }
                }
            }
        }
    }
    private fun ivBack(){
        binding.ivClose.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(R.string.AlertDialog)
                .setCancelable(false)
                .setPositiveButton(R.string.AlertDialogSi) { dialog, id ->
                    super.onBackPressedDispatcher.onBackPressed()
                }
                .setNegativeButton(R.string.AlertDialogNo) { dialog, id ->}
                .show()
        }
    }

    private fun Loading(){
        binding.pb.isVisible = true

    }
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage(R.string.AlertDialog)
            .setCancelable(false)
            .setPositiveButton(R.string.AlertDialogSi) { dialog, id ->
                super.onBackPressedDispatcher.onBackPressed()
            }
            .setNegativeButton(R.string.AlertDialogNo) { dialog, id ->
            }
            .show()
    }

    private fun Success(state:QuizDetailState.Success){
        binding.pb.isVisible = false
        binding.tvQuizQuestion.text = state.pregunta
        binding.tvQuizAlternativeA.text = state.alternativaA
        binding.tvQuizAlternativeB.text = state.alternativaB
        binding.tvQuizAlternativeC.text = state.alternativaC
        binding.tvQuizAlternativeD.text = state.alternativaD
        Quiz(state)
    }



    private fun Error(){
        binding.pb.isVisible = false
        if(contadorPreguntas == 5) {
            sharedViewModel.expProgress(contadorPuntos)
            dialogPoints()
        }else{
            Log.i("mama66", "NO SE HAN RESPONDIDO TODAS LAS PREGUNTAS DEL QUIZ")
        }
    }
    private fun dialogPoints(){
        val dialog = Dialog(this)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.item_dialog_points_quiz)
        dialog.setCancelable(false)

        val btnAcceptLevel = dialog.findViewById<Button>(R.id.btnAcceptExpQuiz)
        val tvPoints = dialog.findViewById<TextView>(R.id.tvPointsQuiz)
        tvPoints.text = "¡Haz Ganado $contadorPuntos puntos de experiencia!"

        btnAcceptLevel.setOnClickListener {
            dialog.hide()
            finish()
        }
        dialog.show()
    }

    private fun btnEnable(){
        binding.btnQuizNext.isEnabled = true
        binding.btnQuizNext.setBackgroundColor(getColor(R.color.color_navBar))
        binding.btnQuizNext.setOnClickListener {
            quizDetailViewModel.mostrarSiguientePregunta()
            resetColor()
            binding.btnQuizNext.isEnabled = false
            binding.btnQuizNext.setBackgroundColor(getColor(R.color.disabled_btn))
        }
        Error()
    }
    private fun resetColor(){
        binding.cvQuizAlternativeA.setCardBackgroundColor(ContextCompat.getColor(this,R.color.background_card))
        binding.cvQuizAlternativeB.setCardBackgroundColor(ContextCompat.getColor(this,R.color.background_card))
        binding.cvQuizAlternativeC.setCardBackgroundColor(ContextCompat.getColor(this,R.color.background_card))
        binding.cvQuizAlternativeD.setCardBackgroundColor(ContextCompat.getColor(this,R.color.background_card))

        binding.tvQuizAlternativeA.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.tvQuizAlternativeB.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.tvQuizAlternativeC.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.tvQuizAlternativeD.setTextColor(ContextCompat.getColor(this, R.color.black))
    }

    private fun handleAnswer(cardView: CardView, isCorrect: Boolean) {
        if (isCorrect) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.color_navBar))
            binding.tvQuizAlternativeA.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tvQuizAlternativeB.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tvQuizAlternativeC.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tvQuizAlternativeD.setTextColor(ContextCompat.getColor(this,R.color.white))

        } else {
            cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.red))
            binding.tvQuizAlternativeA.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tvQuizAlternativeB.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tvQuizAlternativeC.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tvQuizAlternativeD.setTextColor(ContextCompat.getColor(this,R.color.white))
        }
    }

    private fun Quiz(state:QuizDetailState.Success){
        val optionA = 1
        val optionB = 2
        val optionC = 3
        val optionD = 4

        binding.cvQuizAlternativeA.setOnClickListener {
            val isCorrect = state.respuesta == optionA
            if (isCorrect) {
                handleAnswer(binding.cvQuizAlternativeA, true)
                handleAnswer(binding.cvQuizAlternativeB, false)
                handleAnswer(binding.cvQuizAlternativeC, false)
                handleAnswer(binding.cvQuizAlternativeD, false)
                contadorPreguntas+=1
                if(state.dificultad == QuizModel.facil.name){
                    contadorPuntos += 5
                }else{
                    if(state.dificultad == QuizModel.intermedio.name){
                        contadorPuntos += 10
                    }else{
                        if(state.dificultad == QuizModel.dificil.name){
                            contadorPuntos += 15
                        }
                    }
                }

            } else {
                handleAnswer(binding.cvQuizAlternativeA, false)
                handleAnswer(binding.cvQuizAlternativeB, state.respuesta == optionB)
                handleAnswer(binding.cvQuizAlternativeC, state.respuesta == optionC)
                handleAnswer(binding.cvQuizAlternativeD, state.respuesta == optionD)
                contadorPreguntas+=1
            }
            btnEnable()
        }

        binding.cvQuizAlternativeB.setOnClickListener {
            val isCorrect = state.respuesta == optionB
            if (isCorrect) {
                handleAnswer(binding.cvQuizAlternativeB, true)
                handleAnswer(binding.cvQuizAlternativeA, false)
                handleAnswer(binding.cvQuizAlternativeC, false)
                handleAnswer(binding.cvQuizAlternativeD, false)
                contadorPreguntas+=1
                if(state.dificultad == QuizModel.facil.name){
                    contadorPuntos += 5
                }else{
                    if(state.dificultad == QuizModel.intermedio.name){
                        contadorPuntos += 10
                    }else{
                        if(state.dificultad == QuizModel.dificil.name){
                            contadorPuntos += 15
                        }
                    }
                }

            } else {
                handleAnswer(binding.cvQuizAlternativeB, false)
                handleAnswer(binding.cvQuizAlternativeA, state.respuesta == optionA)
                handleAnswer(binding.cvQuizAlternativeC, state.respuesta == optionC)
                handleAnswer(binding.cvQuizAlternativeD, state.respuesta == optionD)
                contadorPreguntas+=1
            }
            btnEnable()
        }

        binding.cvQuizAlternativeC.setOnClickListener {
            val isCorrect = state.respuesta == optionC
            if (isCorrect) {
                handleAnswer(binding.cvQuizAlternativeC, true)
                handleAnswer(binding.cvQuizAlternativeA, false)
                handleAnswer(binding.cvQuizAlternativeB, false)
                handleAnswer(binding.cvQuizAlternativeD, false)
                contadorPreguntas+=1
                if(state.dificultad == QuizModel.facil.name){
                    contadorPuntos += 5
                }else{
                    if(state.dificultad == QuizModel.intermedio.name){
                        contadorPuntos += 10
                    }else{
                        if(state.dificultad == QuizModel.dificil.name){
                            contadorPuntos += 15
                        }
                    }
                }

            } else {
                handleAnswer(binding.cvQuizAlternativeC, false)
                handleAnswer(binding.cvQuizAlternativeA, state.respuesta == optionA)
                handleAnswer(binding.cvQuizAlternativeB, state.respuesta == optionB)
                handleAnswer(binding.cvQuizAlternativeD, state.respuesta == optionD)
                contadorPreguntas+=1
            }
            btnEnable()
        }

        binding.cvQuizAlternativeD.setOnClickListener {
            val isCorrect = state.respuesta == optionD
            if (isCorrect) {
                handleAnswer(binding.cvQuizAlternativeD, true)
                handleAnswer(binding.cvQuizAlternativeA, false)
                handleAnswer(binding.cvQuizAlternativeB, false)
                handleAnswer(binding.cvQuizAlternativeC, false)
                contadorPreguntas+=1
                if(state.dificultad == QuizModel.facil.name){
                    contadorPuntos += 5
                }else{
                    if(state.dificultad == QuizModel.intermedio.name){
                        contadorPuntos += 10
                    }else{
                        if(state.dificultad == QuizModel.dificil.name){
                            contadorPuntos += 15
                        }
                    }
                }

            } else {
                handleAnswer(binding.cvQuizAlternativeD, false)
                handleAnswer(binding.cvQuizAlternativeA, state.respuesta == optionA)
                handleAnswer(binding.cvQuizAlternativeB, state.respuesta == optionB)
                handleAnswer(binding.cvQuizAlternativeC, state.respuesta == optionC)
                contadorPreguntas+=1
            }
            btnEnable()
        }
    }


}