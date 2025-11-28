package com.diagorus.nstretching.shared.stretching.data.routine.exercise.step

import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.actions.StepAction
import com.diagorus.nstretching.shared.util.locale.StringUiData
import kotlinx.coroutines.flow.flowOf
import com.diagorus.nstretching.shared.generated.resources.Res
import com.diagorus.nstretching.shared.generated.resources.congratulations_routine_end
import com.diagorus.nstretching.shared.generated.resources.end

class RoutineEndStep : StretchingExerciseStep(
    nameRes = Res.string.end,
    actions = flowOf(
        StepAction.Say(StringUiData.Resource(Res.string.congratulations_routine_end)),
    ),
)