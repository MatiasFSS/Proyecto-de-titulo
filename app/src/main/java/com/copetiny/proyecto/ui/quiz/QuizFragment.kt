package com.copetiny.proyecto.ui.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.copetiny.proyecto.R
import com.copetiny.proyecto.databinding.FragmentProfileBinding
import com.copetiny.proyecto.databinding.FragmentQuizBinding
import com.copetiny.proyecto.domain.model.quiz.QuizModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : Fragment() {

    private var _binding:FragmentQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI(){
        binding.iconButtonQuiz.setOnClickListener {
            Snackbar.make(it,getString(R.string.quiz_card_description), Snackbar.LENGTH_LONG)
                .setAction("Aceptar"){
                    onDestroy()
                }
                .setTextMaxLines(100)
                .show()
        }

        binding.btnEasy.setOnClickListener {
            Toast.makeText(requireContext(), "estoy haciendo click ", Toast.LENGTH_SHORT).show()
            val type = QuizModel.facil
            findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToQuizDetailActivity(type))
        }
        binding.btnMedium.setOnClickListener {
            val type = QuizModel.intermedio
            findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToQuizDetailActivity(type))
        }
        binding.btnDifficult.setOnClickListener {
            val type = QuizModel.dificil
            findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToQuizDetailActivity(type))
        }
    }

}