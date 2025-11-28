package com.diagorus.nstretching.shared.util.audio

import com.diagorus.nstretching.shared.util.koin.getDefaultDispatcher
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.StringQualifier
import org.koin.core.scope.Scope
import org.koin.dsl.bind
import org.koin.dsl.module

actual val beepToneManagerModule = module {
    single {
        BeepToneManagerImpl(
            getDefaultDispatcher()
        )
    } bind BeepToneManager::class
}