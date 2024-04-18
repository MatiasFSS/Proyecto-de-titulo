package com.copetiny.proyecto.ui.detailencyclopedia

import android.content.Context
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
import com.copetiny.proyecto.domain.model.encyclopedia.EncyclopediaInfo
import com.copetiny.proyecto.domain.model.encyclopedia.EncyclopediaModel
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
        //binding.tvDetailTitle.text = state.nombre_material
        binding.tvDescription.text = state.descripcion
        binding.tvInformation.text = state.informacion

        val image = when(state.encyclopediaModel){
            EncyclopediaModel.Papel -> R.drawable.papel
            EncyclopediaModel.Aluminio -> R.drawable.aluminio
            EncyclopediaModel.Carton -> R.drawable.carton
            EncyclopediaModel.Vidrio -> R.drawable.vidrio
            EncyclopediaModel.Lata -> R.drawable.lata
            EncyclopediaModel.Madera -> R.drawable.madera
            EncyclopediaModel.Bolsas -> R.drawable.bolsa
            EncyclopediaModel.Chatarra -> R.drawable.chatarra
            EncyclopediaModel.PET -> R.drawable.pet
            EncyclopediaModel.Revistas -> R.drawable.revistas
            EncyclopediaModel.Aceite_Vegetal -> R.drawable.aceite
            EncyclopediaModel.Cables -> R.drawable.cables
            EncyclopediaModel.Celulares -> R.drawable.celulares
            EncyclopediaModel.Pilas -> R.drawable.pilas
            EncyclopediaModel.Sanitarios -> R.drawable.sanitario
            EncyclopediaModel.PlasticoN7 -> R.drawable.plasticon7
        }

        val name = when(state.encyclopediaModel){
            EncyclopediaModel.Papel ->  R.string.encyclopedia_Papel
            EncyclopediaModel.Aluminio -> R.string.encyclopedia_Aluminio
            EncyclopediaModel.Carton -> R.string.encyclopedia_Carton
            EncyclopediaModel.Vidrio -> R.string.encyclopedia_Vidrio
            EncyclopediaModel.Madera -> R.string.encyclopedia_Madera
            EncyclopediaModel.Lata -> R.string.encyclopedia_Lata
            EncyclopediaModel.Bolsas -> R.string.encyclopedia_Bolsa
            EncyclopediaModel.Chatarra -> R.string.encyclopedia_Chatarra
            EncyclopediaModel.PET -> R.string.encyclopedia_PET
            EncyclopediaModel.Revistas -> R.string.encyclopedia_Revistas
            EncyclopediaModel.Aceite_Vegetal -> R.string.encyclopedia_Aceite_Vegetal
            EncyclopediaModel.Cables -> R.string.encyclopedia_Cables
            EncyclopediaModel.Celulares -> R.string.encyclopedia_Celulares
            EncyclopediaModel.Pilas -> R.string.encyclopedia_Pilas
            EncyclopediaModel.PlasticoN7 -> R.string.encyclopedia_Plastico_N7
            EncyclopediaModel.Sanitarios -> R.string.encyclopedia_Sanitarios
        }
        val nameString = this.getString(name)
        binding.ivEncyclopediaDetail.setImageResource(image)
        binding.tvDetailTitle.text = nameString
    }
}