package com.copetiny.proyecto.ui.scan

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.copetiny.proyecto.R
import com.copetiny.proyecto.databinding.FragmentScanBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanFragment : Fragment() {

    private var _binding:FragmentScanBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenerBtnScan()
    }

    private fun listenerBtnScan(){
        binding.btnScan.setOnClickListener {initBtnScan()}
    }
    private fun initBtnScan(){
        val scanOptions = ScanOptions()
        scanOptions.setPrompt("Escanea el material que quieres reciclar")
        scanOptions.setBeepEnabled(true)
        scanOptions.setOrientationLocked(true)
        scanOptions.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        barcodeLauncher.launch(scanOptions)
    }

    private val barcodeLauncher = registerForActivityResult(ScanContract()){ result ->
        if(result.contents == null){
            Toast.makeText(requireContext(), "Error al Leer el codigo QR", Toast.LENGTH_SHORT).show()

        }else{
            Toast.makeText(requireContext(), "el valor escaneado es: ${result.contents}", Toast.LENGTH_SHORT).show()
            val type = result.contents
            findNavController().navigate(
                ScanFragmentDirections.actionScanFragmentToScanDetailActivity(type)
            )
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentScanBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


}