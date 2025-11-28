package com.diagorus.nstretching.shared.util.locale

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val localeManagerModule = module {
    singleOf(::LocaleManagerImpl) bind LocaleManager::class
}
