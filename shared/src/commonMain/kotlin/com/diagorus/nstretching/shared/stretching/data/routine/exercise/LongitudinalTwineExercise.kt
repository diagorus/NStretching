package com.diagorus.nstretching.shared.stretching.data.routine.exercise

import com.diagorus.nstretching.shared.generated.resources.Res
import com.diagorus.nstretching.shared.generated.resources.longitudinal_twine
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.ChangeStep
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.PreparationStep
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.StretchStep
import com.diagorus.nstretching.shared.util.config.StretchingConfig
import kotlinx.coroutines.flow.flowOf

class LongitudinalTwineExercise(
    config: StretchingConfig,
) : StretchingExercise(
    nameRes = Res.string.longitudinal_twine,
    steps = flowOf(
        PreparationStep(Res.string.longitudinal_twine, config.longitudinalTwinePreparationStepDuration),
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