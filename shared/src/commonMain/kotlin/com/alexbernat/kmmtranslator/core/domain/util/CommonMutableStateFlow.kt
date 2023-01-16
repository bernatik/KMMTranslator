package com.alexbernat.kmmtranslator.core.domain.util

import kotlinx.coroutines.flow.MutableStateFlow

fun <T> MutableStateFlow<T>.toCommonMutableStateFlow():CommonMutableStateFlow<T> = CommonMutableStateFlow(this)
expect class CommonMutableStateFlow<T>(stateFlow: MutableStateFlow<T>) : MutableStateFlow<T>