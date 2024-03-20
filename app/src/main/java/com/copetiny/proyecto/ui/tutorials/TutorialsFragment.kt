package com.copetiny.proyecto.ui.tutorials

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.copetiny.proyecto.R
import com.copetiny.proyecto.databinding.FragmentTutorialsBinding
import com.copetiny.proyecto.domain.model.tutorials.TutorialsInfo
import com.copetiny.proyecto.domain.model.tutorials.TutorialsModel
import com.copetiny.proyecto.ui.tutorials.adapter.TutorialsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TutorialsFragment : Fragment() {
    private val tutorialsViewModel by viewModels<TutorialsViewModel>()
    private lateinit var tutorialsAdapter: TutorialsAdapter

    private var _binding:FragmentTutorialsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI(){
        initRecyclerViewTutorials()
        initUIstate()
    }

    private fun initRecyclerViewTutorials(){
        tutorialsAdapter = TutorialsAdapter(onItemSelected = {
            val type = when(it){
                TutorialsInfo.Tutorial1 -> TutorialsModel.Tutorial1
                TutorialsInfo.Tutorial2 -> TutorialsModel.Tutorial2
                TutorialsInfo.Tutorial3 -> TutorialsModel.Tutorial3
                TutorialsInfo.Tutorial4 -> TutorialsModel.Tutorial4
                TutorialsInfo.Tutorial5 -> TutorialsModel.Tutorial5
                TutorialsInfo.Tutorial6 -> TutorialsModel.Tutorial6
            }
        })

        binding.rvTutorials.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tutorialsAdapter
        }
    }
    private fun initUIstate(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                tutorialsViewModel.tutorials.collect(){
                    tutorialsAdapter.updateList(it)
                }
            }
        }
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTutorialsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


}