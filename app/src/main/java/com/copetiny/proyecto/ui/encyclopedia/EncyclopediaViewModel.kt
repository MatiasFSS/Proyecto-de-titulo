package com.copetiny.proyecto.ui.encyclopedia

import androidx.lifecycle.ViewModel
import com.copetiny.proyecto.data.provider.EncyclopediaProvider
import com.copetiny.proyecto.domain.model.EncyclopediaInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class EncyclopediaViewModel @Inject constructor(private val encyclopediaProvider:EncyclopediaProvider):ViewModel() {
    private var _encyclopedia = MutableStateFlow<List<EncyclopediaInfo>>(emptyList())
    val encyclopedia:StateFlow<List<EncyclopediaInfo>> = _encyclopedia

    init {
        _encyclopedia.value = encyclopediaProvider.getEncyclopedia()
    }
}