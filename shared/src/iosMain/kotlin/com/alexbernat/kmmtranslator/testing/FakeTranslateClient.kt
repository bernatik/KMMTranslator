package com.alexbernat.kmmtranslator.testing

import com.alexbernat.kmmtranslator.core.domain.language.Language
import com.alexbernat.kmmtranslator.translate.domain.translate.TranslateClient

class FakeTranslateClient: TranslateClient {

    var translatedText = "Test translation"

    override suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String {
        return translatedText
    }
}