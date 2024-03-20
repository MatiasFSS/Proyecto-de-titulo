package com.copetiny.proyecto.data.provider.tutorials

import com.copetiny.proyecto.domain.model.tutorials.TutorialsInfo
import com.copetiny.proyecto.domain.model.tutorials.TutorialsInfo.*
import javax.inject.Inject

class TutorialsProvider @Inject constructor() {

    fun getTutorials():List<TutorialsInfo>{
        return listOf(
            Tutorial1,
            Tutorial2,
            Tutorial3,
            Tutorial4,
            Tutorial5,
            Tutorial6,

        )
    }
}