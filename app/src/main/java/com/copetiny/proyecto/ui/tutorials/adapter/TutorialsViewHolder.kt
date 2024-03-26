package com.copetiny.proyecto.ui.tutorials.adapter

import android.health.connect.datatypes.units.Length
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.copetiny.proyecto.databinding.ItemTutorialsBinding
import com.copetiny.proyecto.domain.model.tutorials.TutorialsInfo

class TutorialsViewHolder(view:View):RecyclerView.ViewHolder(view) {
    private val binding = ItemTutorialsBinding.bind(view)

    fun render(tutorialsInfo: TutorialsInfo, onItemSelected:(TutorialsInfo) -> Unit){
        val context = binding.tvTitleRVTutorials.context

        binding.ivTutorials.setImageResource(tutorialsInfo.img)
        binding.tvTitleRVTutorials.text = context.getString(tutorialsInfo.name)

        binding.parentTutorial.setOnClickListener {
            onItemSelected(tutorialsInfo)
            //Animation(binding.ivTutorials, newLambda = {onItemSelected(tutorialsInfo)})
        }
    }

}