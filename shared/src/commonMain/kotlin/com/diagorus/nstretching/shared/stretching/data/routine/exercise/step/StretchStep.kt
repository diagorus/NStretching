package com.diagorus.nstretching.shared.stretching.data.routine.exercise.step

import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.actions.StepAction
import com.diagorus.nstretching.shared.util.config.StretchingConfig
import kotlinx.coroutines.flow.flowOf
import com.diagorus.nstretching.shared.generated.resources.Res
import com.diagorus.nstretching.shared.generated.resources.stretch

class StretchStep(
    config: StretchingConfig,
) : StretchingExerciseStep(
    nameRes = Res.string.stretch,
    actions = flowOf(
        StepAction.Beep(),
        StepAction.Wait(config.stretchStepSecondsDuration),
    ),
)