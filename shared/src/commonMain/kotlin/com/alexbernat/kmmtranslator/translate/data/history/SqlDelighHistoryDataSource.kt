package com.alexbernat.kmmtranslator.translate.data.history

import com.alexbernat.kmmtranslator.core.domain.util.CommonFlow
import com.alexbernat.kmmtranslator.core.domain.util.toCommonFlow
import com.alexbernat.kmmtranslator.database.TranslateDatabase
import com.alexbernat.kmmtranslator.translate.domain.history.HistoryDataSource
import com.alexbernat.kmmtranslator.translate.domain.history.HistoryItem
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelighHistoryDataSource(
    private val db: TranslateDatabase
) : HistoryDataSource {

    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return db.translateQueries.getHistory()
            .asFlow()
            .mapToList()
            .map { history ->
                history.map {
                    it.toHistoryItem()
                }
            }
            .toCommonFlow()
    }

    override suspend fun insertHistoryItem(item: HistoryItem) {
        db.translateQueries.insertHistoryEntity(
            id = item.id,
            fromLanguageCode = item.fromLanguageCode,
            fromText = item.fromText,
            toLanguageCode = item.toLanguageCode,
            toText = item.toText,
            timestamp = Clock.System.now().toEpochMilliseconds()
        )
    }
}