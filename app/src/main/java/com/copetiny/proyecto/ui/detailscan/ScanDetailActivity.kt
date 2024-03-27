package com.copetiny.proyecto.ui.detailscan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.navArgs
import com.copetiny.proyecto.R
import com.copetiny.proyecto.databinding.ActivityScanDetailBinding
import org.json.JSONObject

class ScanDetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityScanDetailBinding
    val args:ScanDetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initBack()
    }

    private fun initUI(){
        //para acceder a cada dato de un json dentro de un QR
        //val json = JSONObject(args.type)
        //val a = json.getString("Model")
        //val b = json.getString("BIOSVersion")
        binding.tvDetailQR.text = args.type
    }
    private fun initBack(){
        binding.ivBackScan.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

}