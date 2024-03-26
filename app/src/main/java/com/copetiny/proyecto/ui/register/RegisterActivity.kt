package com.copetiny.proyecto.ui.register
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        checkUser()
    }

    private fun checkUser(){
        if(prefs.getName().isNotEmpty()){
            goToProfile()
        }
    }

    private fun initUI() {
        binding.btRegister.setOnClickListener { accessToProfile() }
    }

    private fun accessToProfile(){
        if(binding.etName.text.toString().isNotEmpty()){
            prefs.saveName(binding.etName.text.toString())
            prefs.saveAge(binding.etAge.text.toString())
            goToProfile()
        }else{

        }
    }

    private fun goToProfile(){
        finish()
        startActivity(Intent(this, MainActivity::class.java))

    }


}
