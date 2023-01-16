package com.alexbernat.kmmtranslator.di

import com.alexbernat.kmmtranslator.database.TranslateDatabase
import com.alexbernat.kmmtranslator.translate.data.history.SqlDelighHistoryDataSource
import com.alexbernat.kmmtranslator.translate.data.local.DatabaseDriverFactory
import com.alexbernat.kmmtranslator.translate.data.remote.HttpClientFactory
import com.alexbernat.kmmtranslator.translate.data.translate.KtorTranslateClient
import com.alexbernat.kmmtranslator.translate.domain.history.HistoryDataSource
import com.alexbernat.kmmtranslator.translate.domain.translate.Translate
import com.alexbernat.kmmtranslator.translate.domain.translate.TranslateClient
import com.alexbernat.kmmtranslator.voice_to_text.domain.VoiceToTextParser

interface AppModule {
    val historyDataSource: HistoryDataSource
    val translateClient: TranslateClient
    val translateUseCase: Translate
    val voiceParser: VoiceToTextParser
}

class AppModuleImpl(
    private val parser: VoiceToTextParser
): AppModule {

    override val voiceParser: VoiceToTextParser = parser

    override val historyDataSource: HistoryDataSource by lazy {
        SqlDelighHistoryDataSource(
            TranslateDatabase.invoke(
                DatabaseDriverFactory().create()
            )
        )
    }

    override val translateClient: TranslateClient by lazy {
        KtorTranslateClient(
            HttpClientFactory().create()
        )
    }

    override val translateUseCase: Translate by lazy {
        Translate(
            translateClient, historyDataSource
        )
    }
}