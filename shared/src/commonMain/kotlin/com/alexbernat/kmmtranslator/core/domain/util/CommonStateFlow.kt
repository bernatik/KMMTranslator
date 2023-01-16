package com.alexbernat.kmmtranslator.core.domain.util

import kotlinx.coroutines.flow.StateFlow

fun <T> StateFlow<T>.toCommonStateFlow() = CommonStateFlow(this)
expect class CommonStateFlow<T>(stateFlow: StateFlow<T>) : StateFlow<T>