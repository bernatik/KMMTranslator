package com.alexbernat.kmmtranslator.translate.domain.translate

import com.alexbernat.kmmtranslator.core.domain.language.Language
import com.alexbernat.kmmtranslator.core.domain.util.Resource
import com.alexbernat.kmmtranslator.translate.domain.history.HistoryDataSource
import com.alexbernat.kmmtranslator.translate.domain.history.HistoryItem

class Translate(
    private val client: TranslateClient,
    private val historyDataSource: HistoryDataSource
) {

    suspend fun execute(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): Resource<String> {
        return try {
            val translated = client.translate(
                fromLanguage, fromText, toLanguage
            )

            historyDataSource.insertHistoryItem(
                HistoryItem(
                    id = null,
                    fromLanguageCode = fromLanguage.langCode,
                    fromText = fromText,
                    toLanguageCode = toLanguage.langCode,
                    toText = translated
                )
            )

            Resource.Success(translated)
        } catch (e: TranslateException) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}