package com.diagorus.nstretching.shared.util.koin

import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module

val coroutinesModule = module {
    factory(named<MainDispatcher>()) { Dispatchers.Main }
    factory(named<DefaultDispatcher>()) { Dispatchers.Default }
    factory(named<IoDispatcher>()) { Dispatchers.IO }
    factory {
        CoroutineExceptionHandler { _, exception ->
            Logger.e(exception) { "In injected CoroutineExceptionHandler" }
        }
    }
    factory(named<ApplicationScope>()) {
        CoroutineScope(
            SupervisorJob()
                    + getDefaultDispatcher()
                    + get<CoroutineExceptionHandler>()
        )
    }
}

fun Scope.getIoDispatcher(): CoroutineDispatcher {
    return get(named<IoDispatcher>())
}

fun Scope.getMainDispatcher(): CoroutineDispatcher {
    return get(named<MainDispatcher>())
}

fun Scope.getDefaultDispatcher(): CoroutineDispatcher {
    return get(named<DefaultDispatcher>())
}

fun Scope.getApplicationScope(): CoroutineScope {
    return get(named<ApplicationScope>())
}

class IoDispatcher

class MainDispatcher

class DefaultDispatcher

class ApplicationScope