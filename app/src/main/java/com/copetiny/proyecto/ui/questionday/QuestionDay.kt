package com.copetiny.proyecto.ui.questionday

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
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
import androidx.navigation.findNavController
import com.copetiny.proyecto.R
import com.copetiny.proyecto.databinding.ActivityQuestionDayBinding
import com.copetiny.proyecto.ui.home.MainActivity
import com.copetiny.proyecto.ui.profile.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class QuestionDay : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionDayBinding
    private val questionDayViewModel by viewModels<QuestionDayViewModel>()
    private val sharedViewModel: SharedViewModel by viewModels()
    var puntos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionDayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("QuestionDay", "Activity created")
        initUI()
    }

    private fun initUI() {
        initUIState()
        ivBack()
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                questionDayViewModel.state.collect { state ->
                    Log.i("QuestionDay", "State: $state")
                    when (state) {
                        is QuestionDayState.Error -> Error(state.error)
                        QuestionDayState.Loading -> Loading()
                        is QuestionDayState.Success -> Success(state)
                    }
                }
            }
        }
    }

    private fun ivBack(){
        binding.ivQuestionDayClose.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(R.string.AlertDialog_pregunta_dia)
                .setCancelable(false)
                .setPositiveButton(R.string.AlertDialogSi){dialog, id ->
                    super.onBackPressedDispatcher.onBackPressed()
                }
                .setNegativeButton(R.string.AlertDialogNo){dialog, id ->}
                .show()
        }
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

    private fun Loading() {
        binding.pbQuestionDay.isVisible = true
    }

    private fun Error(error: String) {
        binding.pbQuestionDay.isVisible = false
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    private fun Success(state: QuestionDayState.Success) {
        binding.pbQuestionDay.isVisible = false
        binding.tvQuestionDay.text = state.pregunta
        binding.tvQuestionDayAlternativeA.text = state.alternativaA
        binding.tvQuestionDayAlternativeB.text = state.alternativaB
        binding.tvQuestionDayAlternativeC.text = state.alternativaC
        binding.tvQuestionDayAlternativeD.text = state.alternativaD
        QuestionDayQuiz(state)
    }


    private fun dialogAnswerCorrect(isCorrect: Boolean){
        val dialog = Dialog(this)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.item_dialog_answer)
        dialog.setCancelable(false)

        val btnAccept = dialog.findViewById<Button>(R.id.btnAcceptQuestionDay)
        val tvAnswer = dialog.findViewById<TextView>(R.id.tvAnswerQuestionDay)

        if(isCorrect){
            tvAnswer.text = getString(R.string.tvDialogPointsCorrect)
        }else{
            tvAnswer.text = getString(R.string.tvDialogPointsIncorrect)
        }

        btnAccept.setOnClickListener {
            dialog.hide()
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
        dialog.show()

    }

    private fun QuestionDayQuiz(state:QuestionDayState.Success){
        binding.cvQuestionDayAlternativeA.setOnClickListener {
            val isCorrect = state.respuesta == state.alternativaA
            if(isCorrect){
                handleAnswer(binding.cvQuestionDayAlternativeA, true)
                handleAnswer(binding.cvQuestionDayAlternativeB, false)
                handleAnswer(binding.cvQuestionDayAlternativeC, false)
                handleAnswer(binding.cvQuestionDayAlternativeD, false)
                puntos += 25
                sharedViewModel.expProgress(puntos)

            }else{
                handleAnswer(binding.cvQuestionDayAlternativeA, false)
                handleAnswer(binding.cvQuestionDayAlternativeB, state.respuesta == state.alternativaB)
                handleAnswer(binding.cvQuestionDayAlternativeC, state.respuesta == state.alternativaC)
                handleAnswer(binding.cvQuestionDayAlternativeD, state.respuesta == state.alternativaD)
            }
            dialogAnswerCorrect(isCorrect)
        }

        binding.cvQuestionDayAlternativeB.setOnClickListener {
            val isCorrect = state.respuesta == state.alternativaB
            if(isCorrect){
                handleAnswer(binding.cvQuestionDayAlternativeA, false)
                handleAnswer(binding.cvQuestionDayAlternativeB, true)
                handleAnswer(binding.cvQuestionDayAlternativeC, false)
                handleAnswer(binding.cvQuestionDayAlternativeD, false)
                puntos += 25
                sharedViewModel.expProgress(puntos)
            }else{
                handleAnswer(binding.cvQuestionDayAlternativeA, state.respuesta == state.alternativaA)
                handleAnswer(binding.cvQuestionDayAlternativeB, false)
                handleAnswer(binding.cvQuestionDayAlternativeC, state.respuesta == state.alternativaC)
                handleAnswer(binding.cvQuestionDayAlternativeD, state.respuesta == state.alternativaD)
            }
            dialogAnswerCorrect(isCorrect)
        }

        binding.cvQuestionDayAlternativeC.setOnClickListener {
            val isCorrect = state.respuesta == state.alternativaC
            if(isCorrect){
                handleAnswer(binding.cvQuestionDayAlternativeA, false)
                handleAnswer(binding.cvQuestionDayAlternativeB, false)
                handleAnswer(binding.cvQuestionDayAlternativeC, true)
                handleAnswer(binding.cvQuestionDayAlternativeD, false)
                puntos += 25
                sharedViewModel.expProgress(puntos)
            }else{
                handleAnswer(binding.cvQuestionDayAlternativeA, state.respuesta == state.alternativaA)
                handleAnswer(binding.cvQuestionDayAlternativeB, state.respuesta == state.alternativaB)
                handleAnswer(binding.cvQuestionDayAlternativeC, false)
                handleAnswer(binding.cvQuestionDayAlternativeD, state.respuesta == state.alternativaD)
            }
            dialogAnswerCorrect(isCorrect)
        }

        binding.cvQuestionDayAlternativeD.setOnClickListener {
            val isCorrect = state.respuesta == state.alternativaD
            if(isCorrect){
                handleAnswer(binding.cvQuestionDayAlternativeA, false)
                handleAnswer(binding.cvQuestionDayAlternativeB, false)
                handleAnswer(binding.cvQuestionDayAlternativeC, false)
                handleAnswer(binding.cvQuestionDayAlternativeD, true)
                puntos += 25
                sharedViewModel.expProgress(puntos)
            }else{
                handleAnswer(binding.cvQuestionDayAlternativeA, state.respuesta == state.alternativaA)
                handleAnswer(binding.cvQuestionDayAlternativeB, state.respuesta == state.alternativaB)
                handleAnswer(binding.cvQuestionDayAlternativeC, state.respuesta == state.alternativaC)
                handleAnswer(binding.cvQuestionDayAlternativeD, false)
            }
            dialogAnswerCorrect(isCorrect)
        }
    }


    private fun handleAnswer(cardView: CardView, isCorrect: Boolean) {
        if (isCorrect) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.color_navBar))
            binding.tvQuestionDayAlternativeA.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tvQuestionDayAlternativeB.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tvQuestionDayAlternativeC.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tvQuestionDayAlternativeD.setTextColor(ContextCompat.getColor(this,R.color.white))

        } else {
            cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.red))
            binding.tvQuestionDayAlternativeA.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tvQuestionDayAlternativeB.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tvQuestionDayAlternativeC.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tvQuestionDayAlternativeD.setTextColor(ContextCompat.getColor(this,R.color.white))
        }
    }
}