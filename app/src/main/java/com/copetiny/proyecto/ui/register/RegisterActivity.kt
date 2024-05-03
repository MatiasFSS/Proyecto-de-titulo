package com.copetiny.proyecto.ui.register
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import com.copetiny.proyecto.ProyectoApp.Companion.prefs

import com.copetiny.proyecto.databinding.ActivityRegisterBinding
import com.copetiny.proyecto.ui.home.MainActivity
import com.google.android.material.snackbar.Snackbar


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }



    private fun initUI() {
        binding.btRegister.setOnClickListener { accessToProfile() }
    }

    private fun accessToProfile(){

        if(binding.etName.text.toString().isNotEmpty() && binding.etAge.text.toString().isNotEmpty()){
            if(binding.etAge.text.toString().toIntOrNull() != null && binding.etAge.text.toString().toIntOrNull() in 1..99) {
                prefs.saveName(binding.etName.text.toString())
                prefs.saveAge(binding.etAge.text.toString().toIntOrNull()!!)
                goToProfile()
            }else{
                binding.etAge.error = "Solo se aceptan valores numericos o la edad no es valida"
                Snackbar.make(binding.root,"solo se aceptan valores numericos o la edad no es valida", Snackbar.LENGTH_SHORT).show()
                //Toast.makeText(this,"solo se aceptan valores numericos o la edad no es valida", Toast.LENGTH_SHORT).show()
            }
        }else{
            if(binding.etName.text.toString().isEmpty()){
                binding.etName.error = "Este campo es obligatorio"

                if(binding.etAge.text.toString().isEmpty()){
                    binding.etAge.error = "Este campo es obligatorio"
                    Snackbar.make(binding.root,"Los campos son obligatorios, porfavor Ingrese su nombre y edad", Snackbar.LENGTH_SHORT).show()
                    //Toast.makeText(this,"Los campos son obligatorios, porfavor Ingrese su nombre y edad", Toast.LENGTH_SHORT).show()
                }else{
                    Snackbar.make(binding.root,"El campo es obligatorio, porfavor Ingrese su nombre", Snackbar.LENGTH_SHORT).show()
                    //Toast.makeText(this,"El campo es obligatorio, porfavor Ingrese su nombre", Toast.LENGTH_SHORT).show()
                }
            }else{
                if(binding.etAge.text.toString().isEmpty()){
                    binding.etAge.error = "Este campo es obligatorio"
                    Snackbar.make(binding.root,"El campo es obligatorio, porfavor Ingrese su edad", Snackbar.LENGTH_SHORT).show()
                    //Toast.makeText(this,"El campo es obligatorio, porfavor Ingrese su edad", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goToProfile(){
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }


}
