package com.diagorus.nstretching.shared.stretching.data.routine.exercise.step

import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.actions.StepAction
import com.diagorus.nstretching.shared.util.config.StretchingConfig
import com.diagorus.nstretching.shared.util.locale.StringUiData
import kotlinx.coroutines.flow.flowOf
import com.diagorus.nstretching.shared.generated.resources.Res
import com.diagorus.nstretching.shared.generated.resources.relax

class RelaxStep(
    config: StretchingConfig,
) : StretchingExerciseStep(
    nameRes = Res.string.relax,
    actions = flowOf(
        StepAction.Say(StringUiData.Resource(Res.string.relax)),
        StepAction.Wait(config.relaxStepSecondsHalfDuration),
        StepAction.DoubleBeep(),
        StepAction.Wait(config.relaxStepSecondsHalfDuration),
    )
)