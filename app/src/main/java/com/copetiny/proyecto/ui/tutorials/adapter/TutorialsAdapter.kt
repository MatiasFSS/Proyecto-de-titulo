package com.copetiny.proyecto.ui.tutorials.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.copetiny.proyecto.R
import com.copetiny.proyecto.domain.model.tutorials.TutorialsInfo

class TutorialsAdapter(private var tutorialsList:List<TutorialsInfo> = emptyList(),
    private val onItemSelected:(TutorialsInfo) -> Unit): RecyclerView.Adapter<TutorialsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorialsViewHolder {
        return TutorialsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_tutorials, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return tutorialsList.size
    }

    override fun onBindViewHolder(holder: TutorialsViewHolder, position: Int) {
        holder.render(tutorialsList[position], onItemSelected)
    }

    fun updateList(list:List<TutorialsInfo>){
        tutorialsList = list
        notifyDataSetChanged()
    }
}