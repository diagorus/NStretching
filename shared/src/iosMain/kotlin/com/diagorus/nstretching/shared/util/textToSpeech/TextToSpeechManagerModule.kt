package com.diagorus.nstretching.shared.util.textToSpeech

import com.diagorus.nstretching.shared.util.koin.getDefaultDispatcher
import org.koin.dsl.bind
import org.koin.dsl.module

actual val textToSpeechManagerModule = module {
    single {
        TextToSpeechManagerImpl(
            getDefaultDispatcher(),
            get(),
        )
    } bind TextToSpeechManager::class
}