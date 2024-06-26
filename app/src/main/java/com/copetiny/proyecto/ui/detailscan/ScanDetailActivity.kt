package com.copetiny.proyecto.ui.detailscan

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import com.copetiny.proyecto.R
import com.copetiny.proyecto.databinding.ActivityScanDetailBinding
import com.copetiny.proyecto.ui.bluetooth.BluetoothActivity
import com.copetiny.proyecto.ui.home.MainActivity
import com.copetiny.proyecto.ui.profile.SharedViewModel
import com.copetiny.proyecto.ui.register.PrefsUsers
import org.json.JSONObject
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException


@AndroidEntryPoint
class ScanDetailActivity : AppCompatActivity(){
    private lateinit var binding:ActivityScanDetailBinding
    val args:ScanDetailActivityArgs by navArgs()
    //private val sharedViewModel:SharedViewModel by viewModels()
    private var dialogShown = false
    //var questionPoints = 5



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        ivBack()

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

    private fun handleAnswer(cardView:CardView, isCorrect: Boolean) {
        if (isCorrect) {

            cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.color_navBar))
            //imageView.setColorFilter(ContextCompat.getColor(this, R.color.color_navBar))
            /*binding.tvA.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tvB.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tvC.setTextColor(ContextCompat.getColor(this, R.color.white))*/


        } else {
            cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.red))
            //imageView.setColorFilter(ContextCompat.getColor(this, R.color.red))
            //binding.ivVerde?.setColorFilter(ContextCompat.getColor(this, R.color.color_navBar))
            /*binding.tvA.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tvB.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tvC.setTextColor(ContextCompat.getColor(this, R.color.white))*/

        }
    }
    private fun btnEnable(){
        binding.btnReciclar.isEnabled = true
        binding.btnReciclar.setBackgroundColor(getColor(R.color.color_navBar))
        binding.btnReciclar.setOnClickListener {
            val type = args.type
            val intent = Intent(this, BluetoothActivity::class.java)
            intent.putExtra("type", type) // Pasar datos a BluetoothActivity
            startActivity(intent)
            finish()

        }

        binding.scanAlternativeA.isEnabled = false
        binding.scanAlternativeB.isEnabled = false
        binding.scanAlternativeC.isEnabled = false

    }


    private fun dialogPoints(isCorrect: Boolean){
        if(!dialogShown){
            val dialog = Dialog(this)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.item_dialog_points)

            val json = JSONObject(args.type)
            val imagen = json.getString("imagen")
            val respuesta = json.getString("respuesta")



            val btnAccept = dialog.findViewById<Button>(R.id.btnAccept)
            val tvAnswer = dialog.findViewById<TextView>(R.id.tvAnswer)
            val tvPoints = dialog.findViewById<TextView>(R.id.tvPoints)
            val ivScanImage = dialog.findViewById<ImageView>(R.id.ivScanImage)
            val tvRespuestaScan = dialog.findViewById<TextView>(R.id.tvRespuestaScan)

            if(isCorrect){
                tvAnswer.text = getString(R.string.tvDialogPointsCorrect)
                tvPoints.text = getString(R.string.tvDialogPoints)
                Picasso.get().load(imagen).into(ivScanImage)
                tvRespuestaScan.text = respuesta
            }else{
                tvAnswer.text = getString(R.string.tvDialogPointsIncorrect)
                tvPoints.text = getString(R.string.tvDialogPoints2)
                Picasso.get().load(imagen).into(ivScanImage)
                tvRespuestaScan.text = respuesta
            }

            btnAccept.setOnClickListener {
                dialog.hide()
            }
            dialog.show()
            dialogShown = true
        }

    }

    private fun scanQuestion(){
        val json = JSONObject(args.type)
        val basurero = json.getInt("contenedor")
        val optionA = 1
        val optionB = 2
        val optionC = 3
        val prefsUsers = PrefsUsers(this)

        binding.scanAlternativeA.setOnClickListener {
            val isCorrect = basurero == optionA
            if (isCorrect) {
                handleAnswer(binding.scanAlternativeA, true)
                handleAnswer(binding.scanAlternativeB, false)
                handleAnswer(binding.scanAlternativeC, false)
                //sharedViewModel.expProgress(5)
                dialogPoints(isCorrect)
                prefsUsers.setQuestionFlag(true)



            } else {
                handleAnswer(binding.scanAlternativeA, false)
                handleAnswer(binding.scanAlternativeB, basurero == optionB)
                handleAnswer(binding.scanAlternativeC, basurero == optionC)
                dialogPoints(isCorrect)
                prefsUsers.setQuestionFlag(false)
            }
            btnEnable()
        }

        binding.scanAlternativeB.setOnClickListener {
            val isCorrect = basurero == optionB
            if (isCorrect) {
                handleAnswer(binding.scanAlternativeB, true)
                handleAnswer(binding.scanAlternativeA, false)
                handleAnswer(binding.scanAlternativeC, false)
                //sharedViewModel.expProgress(5)
                dialogPoints(isCorrect)
                prefsUsers.setQuestionFlag(true)

            } else {
                handleAnswer(binding.scanAlternativeB, false)
                handleAnswer(binding.scanAlternativeA, basurero == optionA)
                handleAnswer(binding.scanAlternativeC, basurero == optionC)
                dialogPoints(isCorrect)
                prefsUsers.setQuestionFlag(false)
            }
            btnEnable()
        }

        binding.scanAlternativeC.setOnClickListener {
            val isCorrect = basurero == optionC
            if (isCorrect) {
                handleAnswer(binding.scanAlternativeC, true)
                handleAnswer(binding.scanAlternativeA, false)
                handleAnswer(binding.scanAlternativeB, false)
                //sharedViewModel.expProgress(5)
                dialogPoints(isCorrect)
                prefsUsers.setQuestionFlag(true)

            } else {
                handleAnswer(binding.scanAlternativeC, false)
                handleAnswer(binding.scanAlternativeA, basurero == optionA)
                handleAnswer(binding.scanAlternativeB, basurero == optionB)
                dialogPoints(isCorrect)
                prefsUsers.setQuestionFlag(false)
            }
            btnEnable()
        }

    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage(R.string.AlertDialog)
            .setCancelable(false)
            .setPositiveButton(R.string.AlertDialogSi) { dialog, id ->
                super.onBackPressedDispatcher.onBackPressed()
            }
            .setNegativeButton(R.string.AlertDialogNo) { dialog, id ->
            }
            .show()
    }

    private fun ivBack(){
        binding.ivBackScan.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(R.string.AlertDialog)
                .setCancelable(false)
                .setPositiveButton(R.string.AlertDialogSi) { dialog, id ->
                    super.onBackPressedDispatcher.onBackPressed()
                }
                .setNegativeButton(R.string.AlertDialogNo) { dialog, id ->}
                .show()
        }
    }


}