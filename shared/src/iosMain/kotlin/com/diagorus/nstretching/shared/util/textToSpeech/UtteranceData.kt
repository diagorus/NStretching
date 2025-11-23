package com.diagorus.nstretching.shared.util.textToSpeech

import platform.AVFAudio.AVSpeechUtterance
import kotlin.coroutines.Continuation

data class UtteranceData(
    val utterance: AVSpeechUtterance,
    val continuation: Continuation<Unit>,
)
