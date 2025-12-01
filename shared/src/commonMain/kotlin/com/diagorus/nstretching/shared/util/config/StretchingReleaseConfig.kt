package com.diagorus.nstretching.shared.util.config

class StretchingReleaseConfig : StretchingConfig {

    override val defaultPreparationStepDuration: Double = 10.0
    override val backTwistsPreparationStepDuration: Double = 15.0
    override val changeToBackPreparationStepDuration: Double = 7.5
    override val changeStepSecondsDuration: Double = 5.0
    override val relaxStepSecondsHalfDuration: Double = 5.0
    override val stretchStepSecondsDuration: Double = 30.0
}