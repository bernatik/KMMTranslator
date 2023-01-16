package com.alexbernat.kmmtranslator.core.domain.util

import kotlinx.coroutines.flow.MutableStateFlow

actual class CommonMutableStateFlow<T> actual constructor(stateFlow: MutableStateFlow<T>) :
    MutableStateFlow<T> by stateFlow