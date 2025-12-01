package com.diagorus.nstretching.shared.stretching.ui.viewModel

import androidx.lifecycle.viewModelScope
import com.diagorus.nstretching.shared.stretching.LocaleWithTextToSpeechAvailability
import com.diagorus.nstretching.shared.stretching.data.routine.state.RoutineStateManager
import com.diagorus.nstretching.shared.util.base.BaseViewModel
import com.diagorus.nstretching.shared.util.locale.LocaleManager
import com.diagorus.nstretching.shared.util.textToSpeech.TextToSpeechEngine
import com.diagorus.nstretching.shared.util.textToSpeech.TextToSpeechManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class StretchingRoutineViewModel(
    private val textToSpeechManager: TextToSpeechManager,
    private val localeManager: LocaleManager,
    private val routineStateManager: RoutineStateManager,
) : BaseViewModel<StretchingRoutineUiState>(StretchingRoutineUiState()) {

    init {
        fetchLocaleAvailability()
        fetchTextToSpeechEngines()

        routineStateManager.state
            .onEach { state ->
                updateUiState {
                    copy(
                        routineState = state,
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchLocaleAvailability() {
        viewModelScope.launch {
            val supportedLocales = localeManager.supportedLocales
            val supportedLocalesWithTextToSpeechAvailability = supportedLocales.map {
                val isTextToSpeechAvailable = textToSpeechManager.isLanguageAvailable(it)
                LocaleWithTextToSpeechAvailability(it, isTextToSpeechAvailable)
            }

            val currentLocale = localeManager.getCurrentLocale()
            val currentLocaleWithTextToSpeechAvailability =
                supportedLocalesWithTextToSpeechAvailability.firstOrNull { it.localeWithName == currentLocale }
            updateUiState {
                copy(
                    supportedLocales = supportedLocalesWithTextToSpeechAvailability,
                    currentLocale = currentLocaleWithTextToSpeechAvailability,
                )
            }
        }
    }

    private fun fetchTextToSpeechEngines() {
        viewModelScope.launch {
            val currentEngine = textToSpeechManager.getCurrentEngine()
            val engines = textToSpeechManager.getEngines()
            updateUiState {
                copy(
                    currentTextToSpeechEngine = currentEngine,
                    textToSpeechEngines = engines,
                )
            }
        }
    }

    fun onStartPauseClick() {
        val isTextToSpeechAvailable = uiState.value.currentLocale?.isTextToSpeechAvailable == true
        if (isTextToSpeechAvailable) {
            viewModelScope.launch {
                routineStateManager.startOrPause()
            }
        } else {
            updateUiState {
                copy(
                    showVoiceUnavailableDialog = ShowVoiceUnavailableDialog
                )
            }
        }
    }

    fun onStopClick() {
        routineStateManager.stop()
    }

    fun onLanguageClick(localeWithTextToSpeechAvailability: LocaleWithTextToSpeechAvailability) {
        localeManager.setLocale(localeWithTextToSpeechAvailability.localeWithName)
        fetchLocaleAvailability()
        fetchTextToSpeechEngines()
    }

    fun onEngineClick(engine: TextToSpeechEngine) {
        viewModelScope.launch {
            textToSpeechManager.setEngine(engine)
            fetchLocaleAvailability()
            updateUiState {
                copy(
                    currentTextToSpeechEngine = engine
                )
            }
        }
    }

    fun onVoiceUnavailableDialogDismiss() {
        updateUiState {
            copy(
                showVoiceUnavailableDialog = null
            )
        }
    }

    fun onVoiceUnavailableDialogSettingsClick() {
        onVoiceUnavailableDialogDismiss()
        updateUiState {
            copy(
                showVoiceDownloadSettings = ShowVoiceDownloadSettings
            )
        }
    }

    fun onVoiceDownloadSettingsShown() {
        updateUiState {
            copy(
                showVoiceDownloadSettings = null
            )
        }
    }

    fun onVoiceDownloadSettingsResult() {
        fetchLocaleAvailability()
    }
}
