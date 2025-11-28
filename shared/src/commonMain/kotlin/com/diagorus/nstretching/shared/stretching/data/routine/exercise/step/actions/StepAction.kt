package com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.actions

import com.diagorus.nstretching.shared.util.locale.StringUiData
import kotlin.time.DurationUnit
import kotlin.time.toDuration

sealed interface StepAction {

    class Beep : StepAction

    class Wait(durationInSeconds: Double) : StepAction {
        val duration = durationInSeconds.toDuration(DurationUnit.SECONDS)
    }

    class Say(val text: StringUiData) : StepAction

    class DoubleBeep : StepAction
}