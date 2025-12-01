package com.diagorus.nstretching.shared.stretching.data.routine.state

import com.diagorus.nstretching.shared.util.locale.StringUiData

data class RoutineState(
    val status: RoutineStatus = RoutineStatus.IDLE,
    val exerciseName: StringUiData = StringUiData.Empty,
    val stepName: StringUiData = StringUiData.Empty,
)