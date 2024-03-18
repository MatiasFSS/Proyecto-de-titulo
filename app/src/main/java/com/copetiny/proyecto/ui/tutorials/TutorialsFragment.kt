package com.copetiny.proyecto.ui.tutorials

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.copetiny.proyecto.R
import com.copetiny.proyecto.databinding.FragmentTutorialsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TutorialsFragment : Fragment() {

    private var _binding:FragmentTutorialsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTutorialsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


}