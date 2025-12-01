package com.diagorus.nstretching.shared.stretching.ui.viewModel

import com.diagorus.nstretching.shared.stretching.LocaleWithTextToSpeechAvailability
import com.diagorus.nstretching.shared.stretching.data.routine.state.RoutineState
import com.diagorus.nstretching.shared.stretching.data.routine.state.RoutineStatus
import com.diagorus.nstretching.shared.util.base.BaseUiState
import com.diagorus.nstretching.shared.util.locale.StringUiData
import com.diagorus.nstretching.shared.util.textToSpeech.TextToSpeechEngine

data class StretchingRoutineUiState(
    val currentLocale: LocaleWithTextToSpeechAvailability? = null,
    val supportedLocales: List<LocaleWithTextToSpeechAvailability> = emptyList(),
    val currentTextToSpeechEngine: TextToSpeechEngine? = null,
    val textToSpeechEngines: List<TextToSpeechEngine> = emptyList(),
    val routineState: RoutineState = RoutineState(),
    val showVoiceUnavailableDialog: ShowVoiceUnavailableDialog? = null,
    val showVoiceDownloadSettings: ShowVoiceDownloadSettings? = null,
) : BaseUiState