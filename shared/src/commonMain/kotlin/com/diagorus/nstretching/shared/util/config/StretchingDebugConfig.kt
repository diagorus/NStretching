package com.diagorus.nstretching.shared.util.config

class StretchingDebugConfig : StretchingConfig {

    override val backTwistsPreparationStepDuration: Double = STUB_DURATION
    override val buttAndBackPreparationStepDuration: Double = STUB_DURATION
    override val changeToBackPreparationStepDuration: Double = STUB_DURATION
    override val handsPreparationStepDuration: Double = STUB_DURATION
    override val longitudinalTwinePreparationStepDuration: Double = STUB_DURATION
    override val quadsPreparationStepDuration: Double = STUB_DURATION
    override val transverseTwinePreparationStepDuration: Double = STUB_DURATION
    override val changeStepSecondsDuration: Double = STUB_DURATION
    override val relaxStepSecondsHalfDuration: Double = STUB_DURATION
    override val stretchStepSecondsDuration: Double = STUB_DURATION

    companion object {
        const val STUB_DURATION = 1.0
    }
}