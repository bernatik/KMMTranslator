package com.alexbernat.kmmtranslator.translate.domain.translate

import com.alexbernat.kmmtranslator.core.domain.language.Language

interface TranslateClient {

    suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String
}