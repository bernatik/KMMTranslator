package com.alexbernat.kmmtranslator.voice_to_text.domain

import com.alexbernat.kmmtranslator.core.domain.util.CommonStateFlow

interface VoiceToTextParser {
    val state: CommonStateFlow<VoiceToTextParserState>
    fun startListening(languageCode: String)
    fun stopListening()
    fun cancel()
    fun reset()
}