package com.diagorus.nstretching.shared.stretching.ui.viewModel

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val stretchingRoutineViewModelModule = module {
    viewModelOf(::StretchingRoutineViewModel)
}