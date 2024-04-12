package com.copetiny.proyecto.ui.encyclopedia.adapter

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.copetiny.proyecto.databinding.ItemEncyclopediaBinding
import com.copetiny.proyecto.domain.model.encyclopedia.EncyclopediaInfo

class EncyclopediaViewHolder(view:View):RecyclerView.ViewHolder(view) {
    private val binding = ItemEncyclopediaBinding.bind(view)

    fun render(encyclopediaInfo: EncyclopediaInfo, onItemSelected:(EncyclopediaInfo) -> Unit){
        val context = binding.tvTitle.context
        binding.ivEncyclopedia.setImageResource(encyclopediaInfo.img)
        binding.tvTitle.text = context.getString(encyclopediaInfo.name)

        binding.parent.setOnClickListener {
            //onItemSelected(encyclopediaInfo)
            Animation(binding.ivEncyclopedia, newLambda = {onItemSelected(encyclopediaInfo)})
        }
    }

    fun Animation(view: View, newLambda:() -> Unit){
        view.animate().apply {
            /*duration = 500
            interpolator = LinearInterpolator()
            rotationBy(360f)
            withEndAction{newLambda()}
            start()*/

            scaleX(1.5f)
            scaleY(1.5f)
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
            withEndAction{newLambda()}
            alpha(0f)
            start()

        }
    }
}