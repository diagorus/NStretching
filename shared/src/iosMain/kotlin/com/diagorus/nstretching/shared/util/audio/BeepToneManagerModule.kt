package com.diagorus.nstretching.shared.util.audio

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val beepToneManagerModule = module {
    singleOf(::BeepToneManagerImpl) bind BeepToneManager::class
}