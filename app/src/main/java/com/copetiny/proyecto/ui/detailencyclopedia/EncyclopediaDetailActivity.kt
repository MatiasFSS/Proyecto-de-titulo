package com.copetiny.proyecto.ui.detailencyclopedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import com.copetiny.proyecto.R
import com.copetiny.proyecto.databinding.ActivityEncyclopediaDetailBinding
import com.copetiny.proyecto.domain.model.EncyclopediaModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EncyclopediaDetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityEncyclopediaDetailBinding
    private val encyclopediaDetailViewModel by viewModels<EncyclopediaDetailViewModel>()
    private val args:EncyclopediaDetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEncyclopediaDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        encyclopediaDetailViewModel.getEncyclopedia(args.type)
        initUI()

    }
    private fun initUI(){
        initUIState()
        initBack()
    }
    private fun initBack(){
        binding.ivBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }
    private fun initUIState(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                encyclopediaDetailViewModel.state.collect(){
                    when(it){
                        is EncyclopediaDetailState.Error -> Error()
                        EncyclopediaDetailState.Loading -> Loading()
                        is EncyclopediaDetailState.Success -> Success(it)
                    }
                }
            }
        }
    }

    private fun Loading(){
        binding.pb.isVisible = true
    }
    private fun Error(){
        binding.pb.isVisible = false

    }
    private fun Success(state: EncyclopediaDetailState.Success) {
        binding.pb.isVisible = false
        binding.tvDetailTitle.text = state.nombre_material
        binding.tvDescription.text = state.descripcion
        binding.tvInformation.text = state.informacion

        val image = when(state.encyclopediaModel){
            EncyclopediaModel.Papel -> R.drawable.detail_aries
            EncyclopediaModel.Aluminio -> R.drawable.detail_aries
            EncyclopediaModel.Carton -> R.drawable.detail_aries
            EncyclopediaModel.Vidrio -> R.drawable.detail_aries
            EncyclopediaModel.Lata -> R.drawable.detail_aries
            EncyclopediaModel.Madera -> R.drawable.detail_aries
            EncyclopediaModel.Bolsas -> R.drawable.detail_aries
            EncyclopediaModel.Chatarra -> R.drawable.detail_aries
            EncyclopediaModel.PET -> R.drawable.detail_aries
            EncyclopediaModel.Revistas -> R.drawable.detail_aries
            EncyclopediaModel.Aceite_Vegetal -> R.drawable.detail_aries
            EncyclopediaModel.Cables -> R.drawable.detail_aries
            EncyclopediaModel.Celulares -> R.drawable.detail_aries
            EncyclopediaModel.Pilas -> R.drawable.detail_aries
            EncyclopediaModel.Sanitarios -> R.drawable.detail_aries
            EncyclopediaModel.PlasticoN7 -> R.drawable.detail_aries
        }
        binding.ivEncyclopediaDetail.setImageResource(image)

    }
}