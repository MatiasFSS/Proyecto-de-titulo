package com.copetiny.proyecto.domain

import com.copetiny.proyecto.domain.model.encyclopedia.MaterialModel
import com.copetiny.proyecto.domain.model.tutorials.TutorialModel

interface Repository {
    suspend fun getEncyclopedia(id:String): MaterialModel?
    suspend fun getTutorials(id: String): TutorialModel?
}