package com.copetiny.proyecto.ui.encyclopedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.copetiny.proyecto.R
import com.copetiny.proyecto.domain.model.EncyclopediaInfo

class EncyclopediaAdapter(private var encyclopediaList:List<EncyclopediaInfo> = emptyList(),
    private val onItemSelected:(EncyclopediaInfo) -> Unit):RecyclerView.Adapter<EncyclopediaViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EncyclopediaViewHolder {
        return EncyclopediaViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_encyclopedia, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return encyclopediaList.size
    }

    override fun onBindViewHolder(holder: EncyclopediaViewHolder, position: Int) {
        holder.render(encyclopediaList[position], onItemSelected)
    }

    fun updateList(list:List<EncyclopediaInfo>){
        encyclopediaList = list
        notifyDataSetChanged()
    }
}