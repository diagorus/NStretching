package com.diagorus.nstretching.shared.stretching.data.routine

import com.diagorus.nstretching.shared.stretching.data.routine.exercise.BackTwistsExercise
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.BridgeExercise
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.ButtAndBackExercise
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.HandsExercise
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.LongitudinalTwineExercise
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.QuadsExercise
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.RoutineEndExercise
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.TransverseTwineExercise
import com.diagorus.nstretching.shared.stretching.data.routine.state.RoutineStateManager
import com.diagorus.nstretching.shared.util.koin.getDefaultDispatcher
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val stretchingRoutineModule = module {
    factoryOf(::HandsExercise)
    factoryOf(::BackTwistsExercise)
    factoryOf(::LongitudinalTwineExercise)
    factoryOf(::TransverseTwineExercise)
    factoryOf(::QuadsExercise)
    factoryOf(::ButtAndBackExercise)
    factoryOf(::BridgeExercise)
    factoryOf(::RoutineEndExercise)

    singleOf(::StretchingRoutineRepositoryImpl) bind StretchingRoutineRepository::class
    single {
        RoutineStateManager(
            stretchingRoutineRepository = get(),
            defaultDispatcher = getDefaultDispatcher(),
            textToSpeechManager = get(),
            beepToneManager = get(),
        )
    }
}