package com.alexbernat.kmmtranslator.android.di

import com.alexbernat.kmmtranslator.android.voice_to_text.data.FakeVoiceToTextParser
import com.alexbernat.kmmtranslator.android.translate.data.local.FakeHistoryDatasource
import com.alexbernat.kmmtranslator.translate.data.remote.FakeTranslateClient
import com.alexbernat.kmmtranslator.translate.domain.history.HistoryDataSource
import com.alexbernat.kmmtranslator.translate.domain.translate.Translate
import com.alexbernat.kmmtranslator.translate.domain.translate.TranslateClient
import com.alexbernat.kmmtranslator.voice_to_text.domain.VoiceToTextParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideFakeTranslateClient(): TranslateClient {
        return FakeTranslateClient()
    }

    @Provides
    @Singleton
    fun provideFakeHistoryDataSource(): HistoryDataSource {
        return FakeHistoryDatasource()
    }

    @Provides
    @Singleton
    fun provideFakeTranslateUseCase(
        client: TranslateClient,
        historyDataSource: HistoryDataSource
    ): Translate {
        return Translate(
            client = client,
            historyDataSource = historyDataSource
        )
    }

    @Provides
    @Singleton
    fun provideFakeVoiceToTextParser(): VoiceToTextParser {
        return FakeVoiceToTextParser()
    }
}