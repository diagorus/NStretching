package com.diagorus.nstretching.shared.util.config

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val stretchingDebugConfigModule = module {
    singleOf(::StretchingDebugConfig) bind StretchingConfig::class
}