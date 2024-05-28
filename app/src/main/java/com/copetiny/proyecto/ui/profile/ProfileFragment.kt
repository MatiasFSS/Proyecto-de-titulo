package com.copetiny.proyecto.ui.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.copetiny.proyecto.ProyectoApp.Companion.prefs
import com.copetiny.proyecto.R
import com.copetiny.proyecto.data.network.response.encyclopedia.MaterialResponse
import com.copetiny.proyecto.databinding.FragmentProfileBinding
import com.copetiny.proyecto.domain.model.encyclopedia.Category
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.DecimalFormat


@AndroidEntryPoint

class ProfileFragment : Fragment() {

    private var _binding:FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var current = 0
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(layoutInflater,container,false)
        return binding.root

    }

    private fun initUI() {
        binding.iconButtonProfile.setOnClickListener {
            Snackbar.make(it, getString(R.string.profile_description), Snackbar.LENGTH_LONG)
                .setAction("Aceptar") {
                    onDestroy()
                }
                .setTextMaxLines(100)
                .show()
        }
        binding.btEdit.setOnClickListener {
            /* prefs.wipe()
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToRegisterActivity()
            )*/
            showDialog()
        }
        val userName = prefs.getName()
        val age = prefs.getAge()
        binding.tvName.text = "Nombre: $userName"
        binding.tvAge.text = "Edad: $age"
        levelBar()
    }

    private fun levelBar(){
        binding.rsLevel.isEnabled = false

        sharedViewModel.expProgress.observe(viewLifecycleOwner) {
            val exp = prefs.getExp()
            Log.i("mama5", exp.toString())
            binding.rsLevel.values = listOf(exp.toFloat())
            binding.tvExp.text = "$exp pts"

            val levelObserve = prefs.getLevel()
            binding.tvLevel.text = "lvl $levelObserve"

            if (prefs.updialogFlag()){
                showDialogLevel()
                prefs.setDialogFlag(false)
            }

        }
    }


    private fun showDialogLevel(){
        val dialog = Dialog(requireContext())
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.item_dialog_level)

        val btnAcceptLevel = dialog.findViewById<Button>(R.id.btnAcceptLevel)

        btnAcceptLevel.setOnClickListener { dialog.hide() }

        dialog.show()

    }

    private fun showDialog(){
        val dialog = Dialog(requireContext())
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.item_dialog_edit_user)

        val btn = dialog.findViewById<Button>(R.id.btnConfirm)
        val btnBack = dialog.findViewById<ImageView>(R.id.ivBackDialog)
        val name = dialog.findViewById<EditText>(R.id.edit_name)
        val age = dialog.findViewById<EditText>(R.id.edit_age)

        btn.setOnClickListener {

            if(name.text.toString().isNotEmpty() && age.text.toString().isNotEmpty()){
                if(age.text.toString().toIntOrNull() != null && age.text.toString().toIntOrNull() in 1..99) {
                    prefs.wipe()
                    prefs.saveName(name.text.toString())
                    prefs.saveAge(age.text.toString().toInt())

                    val userName= prefs.getName()
                    val age = prefs.getAge()

                    binding.tvName.text = "Nombre: $userName"
                    binding.tvAge.text = "Edad: $age"
                    dialog.hide()
                }else{
                    age.error = "Solo se aceptan valores numericos o la edad no es valida"
                    Toast.makeText(requireContext(),"solo se aceptan valores numericos o la edad no es valida", Toast.LENGTH_SHORT).show()
                }


            }else{
                if(name.text.toString().isEmpty()){
                    name.error ="Este campo es obligatorio"
                    if(age.text.toString().isEmpty()){
                        age.error = "Este campo es obligatorio"
                        Snackbar.make(it,"Los campos son obligatorios, porfavor Ingrese su nombre y edad", Snackbar.LENGTH_SHORT).show()
                        //Toast.makeText(requireContext(),"Los campos son obligatorios, porfavor Ingrese su nombre y edad", Toast.LENGTH_SHORT).show()

                    }else{
                        Snackbar.make(it,"El campo es obligatorio, porfavor Ingrese su nombre", Snackbar.LENGTH_SHORT).show()
                        //Toast.makeText(requireContext(),"El campo es obligatorio, porfavor Ingrese su nombre", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    if(age.text.toString().isEmpty()){
                        age.error = "Este campo es obligatorio"
                        //Toast.makeText(requireContext(),"El campo es obligatorio, porfavor Ingrese su edad", Toast.LENGTH_SHORT).show()
                        Snackbar.make(it,"El campo es obligatorio, porfavor Ingrese su edad", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
        btnBack.setOnClickListener {dialog.hide()}
        dialog.show()
    }

}