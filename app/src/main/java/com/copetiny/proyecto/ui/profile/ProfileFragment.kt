package com.copetiny.proyecto.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.copetiny.proyecto.ProyectoApp.Companion.prefs
import com.copetiny.proyecto.databinding.FragmentProfileBinding
import com.copetiny.proyecto.ui.register.Prefs
import com.copetiny.proyecto.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class ProfileFragment : Fragment() {

    private var _binding:FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){uri ->
        if(uri != null){
            binding.ivProfile.setImageURI(uri)
        }else{
            Log.i("matias", "Noseleccionado")
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        btnImage()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(layoutInflater,container,false)
        return binding.root

    }

    private fun initUI(){
        binding.btEdit.setOnClickListener {
            prefs.wipe()
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToRegisterActivity()
            )

        }
        val userName= prefs.getName()
        val age = prefs.getAge()
        binding.tvName.text = "Nombre $userName"
        binding.tvAge.text = "Edad: $age"
    }

    private fun btnImage(){
        binding.ivProfile.setOnClickListener {

            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }



}