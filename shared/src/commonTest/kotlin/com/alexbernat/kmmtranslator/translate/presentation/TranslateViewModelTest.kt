package com.alexbernat.kmmtranslator.translate.presentation

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.alexbernat.kmmtranslator.core.presentation.UiLanguage
import com.alexbernat.kmmtranslator.translate.data.local.FakeHistoryDatasource
import com.alexbernat.kmmtranslator.translate.data.remote.FakeTranslateClient
import com.alexbernat.kmmtranslator.translate.domain.history.HistoryItem
import com.alexbernat.kmmtranslator.translate.domain.translate.Translate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

class TranslateViewModelTest {

    private lateinit var viewModel: TranslateViewModel
    private lateinit var datasource: FakeHistoryDatasource
    private lateinit var client: FakeTranslateClient

    @BeforeTest
    fun setup() {
        client = FakeTranslateClient()
        datasource = FakeHistoryDatasource()
        val translate = Translate(client, datasource)
        viewModel = TranslateViewModel(
            translate = translate,
            historyDataSource = datasource,
            coroutineScope = CoroutineScope(Dispatchers.Default)
        )
    }

    @Test
    fun `state and history item are properly combined`() = runBlocking {
        viewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(TranslateState())

            val item = HistoryItem(
                id = 0,
                fromLanguageCode = "en",
                fromText = "from",
                toLanguageCode = "de",
                toText = "to"
            )
            datasource.insertHistoryItem(item)
            val state = awaitItem()

            val expected = UiHistoryItem(
                id = item.id!!,
                fromText = item.fromText,
                toText = item.toText,
                fromLanguage = UiLanguage.byCode(item.fromLanguageCode),
                toLanguage = UiLanguage.byCode(item.toLanguageCode)
            )

            assertThat(state.history.first()).isEqualTo(expected)
        }
    }

    @Test
    fun `translate success - state properly updated`()= runBlocking {
        viewModel.state.test {
            awaitItem()
            viewModel.onEvent(TranslateEvent.ChangeTranslationText("Test"))
            awaitItem()
            viewModel.onEvent(TranslateEvent.Translate)
            val loadingState = awaitItem()
            assertThat(loadingState.isTranslating).isTrue()

            val resultState = awaitItem()
            assertThat(resultState.isTranslating).isFalse()
            assertThat(resultState.toText).isEqualTo(client.translatedText)
        }
    }
}