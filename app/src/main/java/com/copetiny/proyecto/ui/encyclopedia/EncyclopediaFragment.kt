package com.copetiny.proyecto.ui.encyclopedia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.copetiny.proyecto.R
import com.copetiny.proyecto.databinding.FragmentEncyclopediaBinding
import com.copetiny.proyecto.domain.model.encyclopedia.Category
import com.copetiny.proyecto.domain.model.encyclopedia.EncyclopediaInfo
import com.copetiny.proyecto.domain.model.encyclopedia.EncyclopediaModel
import com.copetiny.proyecto.ui.encyclopedia.adapter.EncyclopediaAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EncyclopediaFragment : Fragment() {
    private val encyclopediaViewModel by viewModels<EncyclopediaViewModel>()

    private lateinit var encyclopediaAdapter: EncyclopediaAdapter
    private var _binding: FragmentEncyclopediaBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI(){
        initRecycleView()
        initUIstate()
        setupCategoryButtons()
    }

    private fun initRecycleView(){
        binding.iconButtonMaterial.setOnClickListener {
            Snackbar.make(it,getString(R.string.encyclopedia_Description), Snackbar.LENGTH_LONG)
                .setAction("Aceptar"){
                    onDestroy()
                }
                .setTextMaxLines(100)
                .show()
        }
        encyclopediaAdapter = EncyclopediaAdapter(onItemSelected ={
            val type = when(it){
                EncyclopediaInfo.Aluminio -> EncyclopediaModel.Aluminio
                EncyclopediaInfo.Carton -> EncyclopediaModel.Carton
                EncyclopediaInfo.Lata -> EncyclopediaModel.Lata
                EncyclopediaInfo.Madera -> EncyclopediaModel.Madera
                EncyclopediaInfo.Papel -> EncyclopediaModel.Papel
                EncyclopediaInfo.Vidrio -> EncyclopediaModel.Vidrio
                EncyclopediaInfo.Aceite_Vegetal -> EncyclopediaModel.Aceite_Vegetal
                EncyclopediaInfo.Bolsas -> EncyclopediaModel.Bolsas
                EncyclopediaInfo.Cables -> EncyclopediaModel.Cables
                EncyclopediaInfo.Celulares -> EncyclopediaModel.Celulares
                EncyclopediaInfo.Chatarra -> EncyclopediaModel.Chatarra
                EncyclopediaInfo.PET -> EncyclopediaModel.PET
                EncyclopediaInfo.Pilas -> EncyclopediaModel.Pilas
                EncyclopediaInfo.PlasticoN7 -> EncyclopediaModel.PlasticoN7
                EncyclopediaInfo.Revistas -> EncyclopediaModel.Revistas
                EncyclopediaInfo.Sanitarios -> EncyclopediaModel.Sanitarios
            }
            findNavController().navigate(
                EncyclopediaFragmentDirections.actionEncyclopediaFragmentToEncyclopediaDetailActivity(type)
            )
        })

        binding.rvEncyclopedia.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = encyclopediaAdapter
        }
    }

    private fun initUIstate(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                encyclopediaViewModel.encyclopedia.collect(){
                    encyclopediaAdapter.updateList(it)
                }
            }
        }
    }

    private fun setupCategoryButtons() {
        binding.toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.Reciclables -> encyclopediaViewModel.filterEncyclopediaByCategory(Category.RECICLABLES)
                    R.id.Especiales -> encyclopediaViewModel.filterEncyclopediaByCategory(Category.ESPECIALES)
                    R.id.NoReciclables -> encyclopediaViewModel.filterEncyclopediaByCategory(Category.NO_RECICLABLES)
                }
            } else if (group.checkedButtonId == View.NO_ID) {
                encyclopediaViewModel.resetEncyclopedia()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEncyclopediaBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}