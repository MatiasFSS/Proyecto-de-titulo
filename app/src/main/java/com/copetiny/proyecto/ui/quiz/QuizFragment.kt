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
        binding.btnEasy.setOnClickListener {
            Toast.makeText(requireContext(), "estoy haciendo click ", Toast.LENGTH_SHORT).show()
            findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToQuizDetailActivity())
        }
        binding.btnMedium.setOnClickListener {

            findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToQuizDetailActivity())
        }
        binding.btnDifficult.setOnClickListener {
            findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToQuizDetailActivity())
        }
    }

}