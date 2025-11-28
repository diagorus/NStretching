package com.diagorus.nstretching.shared.stretching.data.routine.exercise.step

import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.actions.StepAction
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.StringResource

abstract class StretchingExerciseStep(
    val nameRes: StringResource,
    val actions: Flow<StepAction>
)