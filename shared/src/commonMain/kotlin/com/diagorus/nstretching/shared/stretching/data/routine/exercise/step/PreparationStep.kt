package com.diagorus.nstretching.shared.stretching.data.routine.exercise.step

import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.actions.StepAction
import com.diagorus.nstretching.shared.util.locale.StringUiData
import kotlinx.coroutines.flow.flowOf
import org.jetbrains.compose.resources.StringResource
import com.diagorus.nstretching.shared.generated.resources.Res
import com.diagorus.nstretching.shared.generated.resources.exercise_get_ready
import com.diagorus.nstretching.shared.generated.resources.preparation

class PreparationStep(
    exerciseNameRes: StringResource,
    durationInSeconds: Double,
) : StretchingExerciseStep(
    nameRes = Res.string.preparation,
    actions = flowOf(
        StepAction.Say(
            StringUiData.Resource(
                Res.string.exercise_get_ready,
                listOf(StringUiData.Argument.StringRes(exerciseNameRes)),
            )
        ),
        StepAction.Wait(durationInSeconds),
    )
)