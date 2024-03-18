package com.copetiny.proyecto.domain.usecase

import com.copetiny.proyecto.domain.Repository
import javax.inject.Inject

class GetEncyclopediaUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(id:String) = repository.getEncyclopedia(id)

}