package com.copetiny.proyecto.ui.detailscan

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import com.copetiny.proyecto.ProyectoApp.Companion.prefs
import com.copetiny.proyecto.R
import com.copetiny.proyecto.databinding.ActivityScanDetailBinding
import com.copetiny.proyecto.ui.profile.SharedViewModel

import org.json.JSONObject

import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanDetailActivity : AppCompatActivity(){
    private lateinit var binding:ActivityScanDetailBinding
    val args:ScanDetailActivityArgs by navArgs()
    private val sharedViewModel:SharedViewModel by viewModels()


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
            binding.tvA.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tvB.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tvC.setTextColor(ContextCompat.getColor(this, R.color.white))


        } else {
            cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.red))
            binding.tvA.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tvB.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tvC.setTextColor(ContextCompat.getColor(this, R.color.white))

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
    private fun dialogPoints(isCorrect: Boolean){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.item_dialog_points)

        val btnAccept = dialog.findViewById<Button>(R.id.btnAccept)
        val tvAnswer = dialog.findViewById<TextView>(R.id.tvAnswer)
        val tvPoints = dialog.findViewById<TextView>(R.id.tvPoints)

        if(isCorrect){
            tvAnswer.text = getString(R.string.tvDialogPointsCorrect)
            tvPoints.text = getString(R.string.tvDialogPoints)

        }else{
            tvAnswer.text = getString(R.string.tvDialogPointsIncorrect)
        }

        btnAccept.setOnClickListener { dialog.hide() }
        dialog.show()
    }

    private fun scanQuestion(){
        val json = JSONObject(args.type)
        val basurero = json.getInt("contenedor")
        val optionA = 1
        val optionB = 2
        val optionC = 3

        binding.scanAlternativeA.setOnClickListener {
            val isCorrect = basurero == optionA
            if (isCorrect) {
                handleAnswer(binding.scanAlternativeA, true)
                handleAnswer(binding.scanAlternativeB, false)
                handleAnswer(binding.scanAlternativeC, false)
                sharedViewModel.expProgress(5)
                dialogPoints(isCorrect)


            } else {
                handleAnswer(binding.scanAlternativeA, false)
                handleAnswer(binding.scanAlternativeB, basurero == optionB)
                handleAnswer(binding.scanAlternativeC, basurero == optionC)
                dialogPoints(isCorrect)
            }
            btnEnable()
        }

        binding.scanAlternativeB.setOnClickListener {
            val isCorrect = basurero == optionB
            if (isCorrect) {
                handleAnswer(binding.scanAlternativeB, true)
                handleAnswer(binding.scanAlternativeA, false)
                handleAnswer(binding.scanAlternativeC, false)
                sharedViewModel.expProgress(5)
                dialogPoints(isCorrect)

            } else {
                handleAnswer(binding.scanAlternativeB, false)
                handleAnswer(binding.scanAlternativeA, basurero == optionA)
                handleAnswer(binding.scanAlternativeC, basurero == optionC)
                dialogPoints(isCorrect)

            }
            btnEnable()
        }

        binding.scanAlternativeC.setOnClickListener {
            val isCorrect = basurero == optionC
            if (isCorrect) {
                handleAnswer(binding.scanAlternativeC, true)
                handleAnswer(binding.scanAlternativeA, false)
                handleAnswer(binding.scanAlternativeB, false)
                sharedViewModel.expProgress(5)
                dialogPoints(isCorrect)

            } else {
                handleAnswer(binding.scanAlternativeC, false)
                handleAnswer(binding.scanAlternativeA, basurero == optionA)
                handleAnswer(binding.scanAlternativeB, basurero == optionB)
                dialogPoints(isCorrect)
            }
            btnEnable()
        }

    }


}