package com.diagorus.nstretching.shared.util.koin

import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.startKoin

fun initKoin(config: KoinAppDeclaration? = null) {
    // KoinDebugApplication or KoinReleaseApplication
    KoinDebugApplication.startKoin(config)
}