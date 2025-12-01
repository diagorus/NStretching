package com.diagorus.nstretching.shared.util.koin

import com.diagorus.nstretching.shared.stretching.data.routine.stretchingRoutineModule
import com.diagorus.nstretching.shared.stretching.ui.viewModel.stretchingRoutineViewModelModule
import com.diagorus.nstretching.shared.util.audio.beepToneManagerModule
import com.diagorus.nstretching.shared.util.config.stretchingDebugConfigModule
import com.diagorus.nstretching.shared.util.config.stretchingReleaseConfigModule
import com.diagorus.nstretching.shared.util.locale.localeManagerModule
import com.diagorus.nstretching.shared.util.preferences.preferencesDataStoreManagerModule
import com.diagorus.nstretching.shared.util.textToSpeech.textToSpeechManagerModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            stretchingReleaseConfigModule,
//            stretchingDebugConfigModule,
            coroutinesModule,
            stretchingRoutineModule,
            stretchingRoutineViewModelModule,

            beepToneManagerModule,
            textToSpeechManagerModule,
            preferencesDataStoreManagerModule,
            localeManagerModule,
        )
    }
}

