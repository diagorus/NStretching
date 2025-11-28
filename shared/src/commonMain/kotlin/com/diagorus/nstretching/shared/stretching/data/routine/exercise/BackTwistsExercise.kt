package com.diagorus.nstretching.shared.stretching.data.routine.exercise

import com.diagorus.nstretching.shared.generated.resources.Res
import com.diagorus.nstretching.shared.generated.resources.back_twists
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.ChangeStep
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.PreparationStep
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.StretchStep
import com.diagorus.nstretching.shared.util.config.StretchingConfig
import kotlinx.coroutines.flow.flowOf

class BackTwistsExercise(
    config: StretchingConfig,
) : StretchingExercise(
    nameRes = Res.string.back_twists,
    steps = flowOf(
        PreparationStep(
            Res.string.back_twists,
            config.backTwistsPreparationStepDuration,
        ),
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