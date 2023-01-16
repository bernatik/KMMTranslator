package com.alexbernat.kmmtranslator.android.translate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexbernat.kmmtranslator.translate.domain.history.HistoryDataSource
import com.alexbernat.kmmtranslator.translate.domain.translate.Translate
import com.alexbernat.kmmtranslator.translate.presentation.TranslateEvent
import com.alexbernat.kmmtranslator.translate.presentation.TranslateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidTranslateViewModel @Inject constructor(
    translate: Translate,
    historyDataSource: HistoryDataSource
) : ViewModel() {

    private val translateViewModel by lazy {
        TranslateViewModel(
            translate = translate,
            historyDataSource = historyDataSource,
            coroutineScope = viewModelScope
        )
    }

    val state = translateViewModel.state

    fun onEvent(event: TranslateEvent) {
        translateViewModel.onEvent(event)
    }
}