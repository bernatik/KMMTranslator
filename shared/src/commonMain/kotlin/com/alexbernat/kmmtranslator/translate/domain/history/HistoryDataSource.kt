package com.alexbernat.kmmtranslator.translate.domain.history

import com.alexbernat.kmmtranslator.core.domain.util.CommonFlow

interface HistoryDataSource {

    fun getHistory(): CommonFlow<List<HistoryItem>>

    suspend fun insertHistoryItem(item: HistoryItem)
}