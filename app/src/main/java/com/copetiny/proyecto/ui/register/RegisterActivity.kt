package com.copetiny.proyecto.ui.register
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.copetiny.proyecto.ProyectoApp.Companion.prefs

import com.copetiny.proyecto.databinding.ActivityRegisterBinding
import com.copetiny.proyecto.ui.home.MainActivity


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
            prefs.saveName(binding.etName.text.toString())
            prefs.saveAge(binding.etAge.text.toString())
            goToProfile()
        }else{
            if(binding.etName.text.toString().isEmpty()){
                binding.etName.error = "Este campo es obligatorio"
                if(binding.etAge.text.toString().isEmpty()){
                    binding.etAge.error = "Este campo es obligatorio"
                    Toast.makeText(this,"Los campos son obligatorios, porfavor Ingrese su nombre y edad", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"El campo es obligatorio, porfavor Ingrese su nombre", Toast.LENGTH_SHORT).show()
                }
            }else{
                if(binding.etAge.text.toString().isEmpty()){
                    binding.etAge.error = "Este campo es obligatorio"
                    Toast.makeText(this,"El campo es obligatorio, porfavor Ingrese su edad", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goToProfile(){
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }


}
