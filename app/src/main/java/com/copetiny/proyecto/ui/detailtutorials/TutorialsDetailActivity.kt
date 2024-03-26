package com.copetiny.proyecto.ui.detailtutorials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import com.copetiny.proyecto.R
import com.copetiny.proyecto.databinding.ActivityTutorialsDetailBinding
import com.copetiny.proyecto.domain.model.tutorials.TutorialsModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
@AndroidEntryPoint
class TutorialsDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTutorialsDetailBinding
    private val tutorialsDetailViewModel by viewModels<TutorialsDetailViewModel>()
    private val args: TutorialsDetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorialsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tutorialsDetailViewModel.getTutorials(args.type)
        initUI()
    }

    private fun initUI(){
        initUIState()
        initBack()
    }

    private fun initBack(){
        binding.ivBackTutorials.setOnClickListener { onBackPressedDispatcher.onBackPressed()}
    }

    private fun initUIState(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                tutorialsDetailViewModel.state.collect(){
                    when(it){
                        is TutorialsDetailState.Error -> Error()
                        TutorialsDetailState.Loading -> Loading()
                        is TutorialsDetailState.Success -> Success(it)
                    }
                }
            }
        }
    }

    private fun Loading(){
        binding.pbTutorials.isVisible = true
    }
    private fun Error(){
        binding.pbTutorials.isVisible = false
    }

    private fun Success(state: TutorialsDetailState.Success){
        binding.pbTutorials.isVisible = false
        binding.tvDetailTutorialsTitle.text = state.nombre_tutorial
        binding.tvDescriptionTutorials.text = state.descripcion_tutorial
        binding.tvDetailTutorialsInformation.text = state.informacion_tutorial

        val image = when(state.tutorialsModel){
            TutorialsModel.Tutorial1 -> R.drawable.reciclaje
            TutorialsModel.Tutorial2 -> R.drawable.cabeza_pasto
            TutorialsModel.Tutorial3 -> R.drawable.titeres
            TutorialsModel.Tutorial4 -> R.drawable.plantar
            TutorialsModel.Tutorial5 -> R.drawable.lapicero
            TutorialsModel.Tutorial6 -> R.drawable.macetero
        }
        binding.ivTutorialsDetail.setImageResource(image)
    }
}