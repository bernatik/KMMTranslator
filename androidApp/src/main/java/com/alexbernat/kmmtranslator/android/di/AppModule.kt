package com.alexbernat.kmmtranslator.android.di

import android.app.Application
import com.alexbernat.kmmtranslator.database.TranslateDatabase
import com.alexbernat.kmmtranslator.translate.data.history.SqlDelighHistoryDataSource
import com.alexbernat.kmmtranslator.translate.data.local.DatabaseDriverFactory
import com.alexbernat.kmmtranslator.translate.data.remote.HttpClientFactory
import com.alexbernat.kmmtranslator.translate.data.translate.KtorTranslateClient
import com.alexbernat.kmmtranslator.translate.domain.history.HistoryDataSource
import com.alexbernat.kmmtranslator.translate.domain.translate.Translate
import com.alexbernat.kmmtranslator.translate.domain.translate.TranslateClient
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClientFactory().create()
    }

    @Provides
    @Singleton
    fun provideTranslateClient(httpClient: HttpClient): TranslateClient {
        return KtorTranslateClient(httpClient)
    }

    @Provides
    @Singleton
    fun provideDatabaseDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).create()
    }

    @Provides
    @Singleton
    fun provideHistoryDataSource(driver: SqlDriver): HistoryDataSource {
        return SqlDelighHistoryDataSource(TranslateDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        client: TranslateClient,
        dataSource: HistoryDataSource
    ): Translate {
        return Translate(client, dataSource)
    }
}