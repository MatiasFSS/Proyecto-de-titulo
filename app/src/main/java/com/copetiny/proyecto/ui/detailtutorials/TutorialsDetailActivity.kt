package com.copetiny.proyecto.ui.detailtutorials

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
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
        //binding.tvDetailTutorialsTitle.text = state.nombre_tutorial
        binding.tvDescriptionTutorials.text = state.descripcion_tutorial
        binding.tvDetailTutorialsInformation.text = state.informacion_tutorial

        val image = when(state.tutorialsModel){
            TutorialsModel.Tutorial1 -> R.drawable.reciclaje_tutorial
            TutorialsModel.Tutorial2 -> R.drawable.cabeza_pasto
            TutorialsModel.Tutorial3 -> R.drawable.titeres
            TutorialsModel.Tutorial4 -> R.drawable.plantar
            TutorialsModel.Tutorial5 -> R.drawable.lapicero
            TutorialsModel.Tutorial6 -> R.drawable.macetero
        }


        val name = when(state.tutorialsModel){
            TutorialsModel.Tutorial1 -> R.string.tutorial_1
            TutorialsModel.Tutorial2 -> R.string.tutorial_2
            TutorialsModel.Tutorial3 -> R.string.tutorial_3
            TutorialsModel.Tutorial4 -> R.string.tutorial_4
            TutorialsModel.Tutorial5 -> R.string.tutorial_5
            TutorialsModel.Tutorial6 -> R.string.tutorial_6
        }

        val image2 = when(state.tutorialsModel){
            TutorialsModel.Tutorial1 -> R.drawable.contenedoresreciclaje
            TutorialsModel.Tutorial2 -> R.drawable.cabezadepastodetalle
            TutorialsModel.Tutorial3 -> R.drawable.titeresdetalles
            TutorialsModel.Tutorial4 -> R.drawable.plantardetalle
            TutorialsModel.Tutorial5 -> R.drawable.lapicerodetalle
            TutorialsModel.Tutorial6 -> R.drawable.maceterodetalle
        }

        val video = when(state.tutorialsModel){
            TutorialsModel.Tutorial1 -> "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/0i99_Lkvjm0?si=5Eo9OMm9kc8ayyPY\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>"
            TutorialsModel.Tutorial2 -> "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/H1TORSSlF_0?si=PPWeGJAjCvnfRpob\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>"
            TutorialsModel.Tutorial3 -> "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/oleKD8xWDTU?si=9_6hrZobCuTUxanW\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>"
            TutorialsModel.Tutorial4 -> "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/-ibcM5TwKao?si=0yR5PG7YxkeXrg3j\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>"
            TutorialsModel.Tutorial5 -> "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/Ik5Fb2MAhqE?si=KJ0q2I7OhJvBmQyy\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>"
            TutorialsModel.Tutorial6 -> "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/7N746C2mB4o?si=B_k6EaGVgG0s1ivw\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>"
        }

        val nameString = this.getString(name)
        binding.ivTutorialsDetail.setImageResource(image)
        binding.ivInformationDetailTutorial.setImageResource(image2)
        binding.tvDetailTutorialsTitle.text = nameString

        webYoutube(video)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webYoutube(video: String){
        val webView = findViewById<WebView>(R.id.video)
        webView.loadData(video, "text/html", "utf-8")

        webView.settings.javaScriptEnabled = true

        webView.webChromeClient = WebChromeClient()
    }
}