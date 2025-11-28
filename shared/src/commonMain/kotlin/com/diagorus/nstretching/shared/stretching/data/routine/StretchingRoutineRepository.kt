package com.diagorus.nstretching.shared.stretching.data.routine

import com.diagorus.nstretching.shared.stretching.data.routine.exercise.StretchingExercise
import kotlinx.coroutines.flow.Flow

interface StretchingRoutineRepository {

    fun getRoutine(): Flow<StretchingExercise>
}