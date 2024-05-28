package com.copetiny.proyecto.ui.encyclopedia

import androidx.lifecycle.ViewModel
import com.copetiny.proyecto.data.provider.encyclopedia.EncyclopediaProvider
import com.copetiny.proyecto.domain.model.encyclopedia.Category
import com.copetiny.proyecto.domain.model.encyclopedia.EncyclopediaInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class EncyclopediaViewModel @Inject constructor(private val encyclopediaProvider: EncyclopediaProvider):ViewModel() {
    private var _encyclopedia = MutableStateFlow<List<EncyclopediaInfo>>(emptyList())
    val encyclopedia:StateFlow<List<EncyclopediaInfo>> = _encyclopedia

    init {
        _encyclopedia.value = encyclopediaProvider.getEncyclopedia()
    }
    fun filterEncyclopediaByCategory(category: Category) {
        val filteredList = when (category) {
            Category.RECICLABLES -> encyclopediaProvider.getEncyclopedia().filter { it.isReciclable }
            Category.ESPECIALES -> encyclopediaProvider.getEncyclopedia().filter { it.isEspecial }
            Category.NO_RECICLABLES -> encyclopediaProvider.getEncyclopedia().filter { !it.isReciclable && !it.isEspecial }
        }
        _encyclopedia.value = filteredList
    }

    fun resetEncyclopedia() {
        _encyclopedia.value = encyclopediaProvider.getEncyclopedia()
    }
}