package com.diagorus.nstretching.shared.util.config

class StretchingReleaseConfig : StretchingConfig {

    override val backTwistsPreparationStepDuration: Double = 15.0
    override val buttAndBackPreparationStepDuration: Double = 10.0
    override val changeToBackPreparationStepDuration: Double = 7.5
    override val handsPreparationStepDuration: Double = 10.0
    override val longitudinalTwinePreparationStepDuration: Double = 10.0
    override val quadsPreparationStepDuration: Double = 10.0
    override val transverseTwinePreparationStepDuration: Double = 10.0
    override val changeStepSecondsDuration: Double = 5.0
    override val relaxStepSecondsHalfDuration: Double = 5.0
    override val stretchStepSecondsDuration: Double = 30.0
}