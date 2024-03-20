package com.copetiny.proyecto.ui.tutorials

import androidx.lifecycle.ViewModel
import com.copetiny.proyecto.data.provider.tutorials.TutorialsProvider
import com.copetiny.proyecto.domain.model.tutorials.TutorialsInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TutorialsViewModel @Inject constructor(private val tutorialsProvider: TutorialsProvider): ViewModel() {
    private var _tutorials = MutableStateFlow<List<TutorialsInfo>>(emptyList())
    val tutorials:StateFlow<List<TutorialsInfo>> = _tutorials

    init {
        _tutorials.value = tutorialsProvider.getTutorials()
    }
}