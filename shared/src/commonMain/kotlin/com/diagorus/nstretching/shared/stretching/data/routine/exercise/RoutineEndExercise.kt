package com.diagorus.nstretching.shared.stretching.data.routine.exercise

import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.RoutineEndStep
import kotlinx.coroutines.flow.flowOf
import com.diagorus.nstretching.shared.generated.resources.Res
import com.diagorus.nstretching.shared.generated.resources.end

class RoutineEndExercise : StretchingExercise(
    nameRes = Res.string.end,
    steps = flowOf(
        RoutineEndStep(),
    ),
)