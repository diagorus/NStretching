package com.diagorus.nstretching.shared.stretching.data.routine

import com.diagorus.nstretching.shared.stretching.data.routine.exercise.BackTwistsExercise
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.ButtAndBackExercise
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.HandsExercise
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.LongitudinalTwineExercise
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.QuadsExercise
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.RoutineEndExercise
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.StretchingExercise
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.TransverseTwineExercise
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class StretchingRoutineRepositoryImpl(
    private val handsExercise: HandsExercise,
    private val backTwistsExercise: BackTwistsExercise,
    private val longitudinalTwineExercise: LongitudinalTwineExercise,
    private val transverseTwineExercise: TransverseTwineExercise,
    private val quadsExercise: QuadsExercise,
    private val buttAndBackExercise: ButtAndBackExercise,
    private val bridgeExercise: ButtAndBackExercise,
    private val routineEndExercise: RoutineEndExercise,
): StretchingRoutineRepository {

    override fun getRoutine(): Flow<StretchingExercise> {
        return flowOf(
            handsExercise,
            backTwistsExercise,
            longitudinalTwineExercise,
            transverseTwineExercise,
            quadsExercise,
            buttAndBackExercise,
            bridgeExercise,
            routineEndExercise,
        )
    }
}