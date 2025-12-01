package com.diagorus.nstretching.shared.stretching.data.routine.exercise

import com.diagorus.nstretching.shared.generated.resources.Res
import com.diagorus.nstretching.shared.generated.resources.quads
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.ChangeStep
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.PreparationStep
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.StretchStep
import com.diagorus.nstretching.shared.util.config.StretchingConfig
import kotlinx.coroutines.flow.flowOf

class QuadsExercise(
    config: StretchingConfig,
) : StretchingExercise(
    nameRes = Res.string.quads,
    steps = flowOf(
        PreparationStep(Res.string.quads, config.defaultPreparationStepDuration),
        StretchStep(config),
        ChangeStep(config),
        StretchStep(config),
        ChangeStep(config),
        StretchStep(config),
        ChangeStep(config),
        StretchStep(config),
        ChangeStep(config),
        StretchStep(config),
        ChangeStep(config),
        StretchStep(config),
    ),
)