package com.alexbernat.kmmtranslator.translate.domain.translate

enum class TranslateError {
    SERVICE_UNAVAILABLE,
    SERVER_ERROR,
    CLIENT_ERROR,
    UNKNOWN_ERROR
}

class TranslateException(val error: TranslateError) : Exception("Translate error: $error")
