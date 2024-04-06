package com.copetiny.proyecto.domain.model.tutorials

import com.copetiny.proyecto.R

sealed class TutorialsInfo(val img:Int, val name:Int) {
    data object Tutorial1: TutorialsInfo(R.drawable.reciclaje_tutorial, R.string.tutorial_1)
    data object Tutorial2: TutorialsInfo(R.drawable.cabeza_pasto, R.string.tutorial_2)
    data object Tutorial3: TutorialsInfo(R.drawable.titeres, R.string.tutorial_3)
    data object Tutorial4: TutorialsInfo(R.drawable.plantar, R.string.tutorial_4)
    data object Tutorial5: TutorialsInfo(R.drawable.lapicero, R.string.tutorial_5)
    data object Tutorial6: TutorialsInfo(R.drawable.macetero, R.string.tutorial_6)
}