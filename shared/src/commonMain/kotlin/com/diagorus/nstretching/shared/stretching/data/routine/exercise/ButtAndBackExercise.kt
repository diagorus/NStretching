package com.diagorus.nstretching.shared.stretching.data.routine.exercise

import com.diagorus.nstretching.shared.generated.resources.Res
import com.diagorus.nstretching.shared.generated.resources.back
import com.diagorus.nstretching.shared.generated.resources.butt_and_back
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.ChangeStep
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.PreparationStep
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.RelaxStep
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.StretchStep
import com.diagorus.nstretching.shared.util.config.StretchingConfig
import kotlinx.coroutines.flow.flowOf

class ButtAndBackExercise(
    config: StretchingConfig,
) : StretchingExercise(
    nameRes = Res.string.butt_and_back,
    steps = flowOf(
        PreparationStep(Res.string.butt_and_back, config.defaultPreparationStepDuration),
        StretchStep(config),
        ChangeStep(config),
        StretchStep(config),
        PreparationStep(Res.string.back, config.defaultPreparationStepDuration),
        StretchStep(config),
        RelaxStep(config),
        StretchStep(config),
        ChangeStep(config),
        StretchStep(config),
        PreparationStep(Res.string.back, config.defaultPreparationStepDuration),
        StretchStep(config),
        RelaxStep(config),
        StretchStep(config),
        ChangeStep(config),
        StretchStep(config),
        PreparationStep(Res.string.back, config.defaultPreparationStepDuration),
        StretchStep(config),
    ),
)