package com.alexbernat.kmmtranslator.di

import com.alexbernat.kmmtranslator.testing.FakeHistoryDatasource
import com.alexbernat.kmmtranslator.testing.FakeTranslateClient
import com.alexbernat.kmmtranslator.testing.FakeVoiceToTextParser
import com.alexbernat.kmmtranslator.translate.domain.history.HistoryDataSource
import com.alexbernat.kmmtranslator.translate.domain.translate.Translate
import com.alexbernat.kmmtranslator.translate.domain.translate.TranslateClient
import com.alexbernat.kmmtranslator.voice_to_text.domain.VoiceToTextParser

class TestAppModule : AppModule {

    override val historyDataSource: HistoryDataSource = FakeHistoryDatasource()
    override val translateClient: TranslateClient = FakeTranslateClient()
    override val translateUseCase: Translate = Translate(translateClient, historyDataSource)
    override val voiceParser: VoiceToTextParser = FakeVoiceToTextParser()
}