package com.diagorus.nstretching.shared.stretching.data.routine

import com.diagorus.nstretching.shared.stretching.data.routine.exercise.BackTwistsExercise
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.ButtAndBackExercise
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.HandsExercise
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.LongitudinalTwineExercise
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.QuadsExercise
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.RoutineEndExercise
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.TransverseTwineExercise
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val stretchingRoutineModule = module {
    singleOf(::StretchingRoutineRepositoryImpl) bind StretchingRoutineRepository::class
    factoryOf(::HandsExercise)
    factoryOf(::BackTwistsExercise)
    factoryOf(::LongitudinalTwineExercise)
    factoryOf(::TransverseTwineExercise)
    factoryOf(::QuadsExercise)
    factoryOf(::ButtAndBackExercise)
    factoryOf(::RoutineEndExercise)
}