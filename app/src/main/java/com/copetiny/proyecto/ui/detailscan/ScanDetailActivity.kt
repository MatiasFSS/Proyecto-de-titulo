package com.copetiny.proyecto.ui.detailscan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import com.copetiny.proyecto.R
import com.copetiny.proyecto.databinding.ActivityScanDetailBinding
import com.copetiny.proyecto.ui.profile.ProfileFragment
import org.json.JSONObject
//import android.util.Base64
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanDetailActivity : AppCompatActivity(){
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
        val json = JSONObject(args.type)
        val title = json.getString("nombre_material")
        val description = json.getString("descripcion")
        val imagen = json.getString("imagen")

        binding.tvTitleQR.text = title
        binding.tvDesciptionScan.text = description
        Picasso.get().load(imagen).into(binding.ivScan)
        scanQuestion()



    }
    private fun initBack(){
        binding.ivBackScan.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun handleAnswer(cardView:CardView, isCorrect: Boolean) {
        if (isCorrect) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.color_navBar))
        } else {
            cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
    }
    private fun btnEnable(){
        binding.btnReciclar.isEnabled = true
        binding.btnReciclar.setBackgroundColor(getColor(R.color.color_navBar))
        binding.btnReciclar.setOnClickListener {
            Toast.makeText(this, "Comienza el proceso de reciclaje", Toast.LENGTH_SHORT).show()
        }

        binding.scanAlternativeA.isEnabled = false
        binding.scanAlternativeB.isEnabled = false
        binding.scanAlternativeC.isEnabled = false

    }
    private fun scanQuestion(){
        val json = JSONObject(args.type)
        val basurero = json.getInt("basurero")
        val optionA = 1
        val optionB = 2
        val optionC = 3

        binding.scanAlternativeA.setOnClickListener {
            val isCorrect = basurero == optionA
            if (isCorrect) {
                Toast.makeText(this, "Respuesta Correcta, Ganas 5 pts de experiencia", Toast.LENGTH_SHORT).show()

                handleAnswer(binding.scanAlternativeA, true)
                handleAnswer(binding.scanAlternativeB, false)
                handleAnswer(binding.scanAlternativeC, false)
            } else {
                Toast.makeText(this, "Respuesta Incorrecta", Toast.LENGTH_SHORT).show()
                handleAnswer(binding.scanAlternativeA, false)
                handleAnswer(binding.scanAlternativeB, basurero == optionB)
                handleAnswer(binding.scanAlternativeC, basurero == optionC)
            }
            btnEnable()
        }

        binding.scanAlternativeB.setOnClickListener {
            val isCorrect = basurero == optionB
            if (isCorrect) {
                Toast.makeText(this, "Respuesta Correcta, Ganas 5 pts de experiencia", Toast.LENGTH_SHORT).show()

                handleAnswer(binding.scanAlternativeB, true)
                handleAnswer(binding.scanAlternativeA, false)
                handleAnswer(binding.scanAlternativeC, false)
            } else {
                Toast.makeText(this, "Respuesta Incorrecta", Toast.LENGTH_SHORT).show()
                handleAnswer(binding.scanAlternativeB, false)
                handleAnswer(binding.scanAlternativeA, basurero == optionA)
                handleAnswer(binding.scanAlternativeC, basurero == optionC)

            }
            btnEnable()
        }

        binding.scanAlternativeC.setOnClickListener {
            val isCorrect = basurero == optionC
            if (isCorrect) {
                Toast.makeText(this, "Respuesta Correcta, Ganas 5 pts de experiencia", Toast.LENGTH_SHORT).show()

                handleAnswer(binding.scanAlternativeC, true)
                handleAnswer(binding.scanAlternativeA, false)
                handleAnswer(binding.scanAlternativeB, false)
            } else {
                Toast.makeText(this, "Respuesta Incorrecta", Toast.LENGTH_SHORT).show()
                handleAnswer(binding.scanAlternativeC, false)
                handleAnswer(binding.scanAlternativeA, basurero == optionA)
                handleAnswer(binding.scanAlternativeB, basurero == optionB)
            }
            btnEnable()
        }

    }


}