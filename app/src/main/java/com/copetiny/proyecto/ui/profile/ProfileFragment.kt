package com.copetiny.proyecto.ui.profile

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.copetiny.proyecto.ProyectoApp.Companion.prefs
import com.copetiny.proyecto.R
import com.copetiny.proyecto.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat


@AndroidEntryPoint

class ProfileFragment : Fragment() {

    private var _binding:FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private var current: Int = 0

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

    private fun initUI(){
        binding.btEdit.setOnClickListener {
           /* prefs.wipe()
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToRegisterActivity()
            )*/
            showDialog()


        }
        val userName= prefs.getName()
        val age = prefs.getAge()
        binding.tvName.text = "Nombre: $userName"
        binding.tvAge.text = "Edad: $age"
        levelBar()


    }

    private fun levelBar(){
        //binding.rsLevel.isEnabled = false

        binding.rsLevel.addOnChangeListener { _, value, _ ->
            val df = DecimalFormat("#.##")
            this.current = df.format(value).toInt()
            binding.tvLevel.text = "$current pts"
        }
    }

    private fun showDialog(){
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.item_dialog_edit_user)

        val btn = dialog.findViewById<Button>(R.id.btnConfirm)
        val btnBack = dialog.findViewById<ImageView>(R.id.ivBackDialog)
        val name = dialog.findViewById<EditText>(R.id.edit_name)
        val age = dialog.findViewById<EditText>(R.id.edit_age)

        btn.setOnClickListener {
            if(name.text.toString().isNotEmpty() && age.text.toString().isNotEmpty()){
                prefs.wipe()
                prefs.saveName(name.text.toString())
                prefs.saveAge(age.text.toString())

                val userName= prefs.getName()
                val age = prefs.getAge()

                binding.tvName.text = "Nombre: $userName"
                binding.tvAge.text = "Edad: $age"
                dialog.hide()
            }else{
                if(name.text.toString().isEmpty()){
                    name.error ="Este campo es obligatorio"
                    if(age.text.toString().isEmpty()){
                        age.error = "Este campo es obligatorio"
                        Toast.makeText(requireContext(),"Los campos son obligatorios, porfavor Ingrese su nombre y edad", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(),"El campo es obligatorio, porfavor Ingrese su nombre", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    if(age.text.toString().isEmpty()){
                        age.error = "Este campo es obligatorio"
                        Toast.makeText(requireContext(),"El campo es obligatorio, porfavor Ingrese su edad", Toast.LENGTH_SHORT).show()
                    }
            }
        }
        }
        btnBack.setOnClickListener {dialog.hide()}
        dialog.show()
    }

}