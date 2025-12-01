package com.diagorus.nstretching.shared.util.config

interface StretchingConfig {
    val defaultPreparationStepDuration: Double
    val backTwistsPreparationStepDuration: Double
    val changeToBackPreparationStepDuration: Double
    val changeStepSecondsDuration: Double
    val relaxStepSecondsHalfDuration: Double
    val stretchStepSecondsDuration: Double
}