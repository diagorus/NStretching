package com.diagorus.nstretching.shared.stretching.data.routine.exercise

import com.diagorus.nstretching.shared.generated.resources.Res
import com.diagorus.nstretching.shared.generated.resources.hands
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.PreparationStep
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.RelaxStep
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.StretchStep
import com.diagorus.nstretching.shared.util.config.StretchingConfig
import kotlinx.coroutines.flow.flowOf

class HandsExercise(
    config: StretchingConfig,
) : StretchingExercise(
    nameRes = Res.string.hands,
    steps = flowOf(
        PreparationStep(Res.string.hands, config.defaultPreparationStepDuration),
        StretchStep(config),
        RelaxStep(config),
        StretchStep(config),
        RelaxStep(config),
        StretchStep(config),
    )
)