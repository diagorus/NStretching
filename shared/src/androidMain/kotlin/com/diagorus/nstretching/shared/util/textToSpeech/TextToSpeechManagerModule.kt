package com.diagorus.nstretching.shared.util.textToSpeech

import com.diagorus.nstretching.shared.util.koin.ApplicationScope
import com.diagorus.nstretching.shared.util.koin.getApplicationScope
import kotlinx.coroutines.CoroutineScope
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

actual val textToSpeechManagerModule = module {
    single {
        TextToSpeechManagerImpl(
            getApplicationScope(),
            get(),
            get(),
            get(),
        )
    } bind TextToSpeechManager::class

    single {
        TextToSpeechInitializationManager(
            get(),
            getApplicationScope(),
            get(),
            get(),
            get(),
        )
    }

    singleOf(::UtteranceManager)
}