package com.diagorus.nstretching.shared.stretching.ui.viewModel

import androidx.lifecycle.viewModelScope
import com.diagorus.nstretching.shared.stretching.LocaleWithTextToSpeechAvailability
import com.diagorus.nstretching.shared.stretching.data.routine.StretchingRoutineRepository
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.actions.StepAction
import com.diagorus.nstretching.shared.util.audio.BeepToneManager
import com.diagorus.nstretching.shared.util.base.BaseViewModel
import com.diagorus.nstretching.shared.util.locale.LocaleManager
import com.diagorus.nstretching.shared.util.locale.StringUiData
import com.diagorus.nstretching.shared.util.pausingCoroutines.PausingJob
import com.diagorus.nstretching.shared.util.pausingCoroutines.launchInPausing
import com.diagorus.nstretching.shared.util.textToSpeech.TextToSpeechEngine
import com.diagorus.nstretching.shared.util.textToSpeech.TextToSpeechManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class StretchingRoutineViewModel(
    private val beepToneManager: BeepToneManager,
    private val textToSpeechManager: TextToSpeechManager,
    private val localeManager: LocaleManager,
    private val stretchingRoutineRepository: StretchingRoutineRepository,
) : BaseViewModel<StretchingRoutineUiState>(StretchingRoutineUiState()) {

    private var routineJob: PausingJob? = null

    init {
        fetchLocaleAvailability()
        fetchTextToSpeechEngines()
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
            startOrPause()
        } else {
            updateUiState {
                copy(
                    showVoiceUnavailableDialog = ShowVoiceUnavailableDialog
                )
            }
        }
    }

    private fun startOrPause() {
        val isRunning = uiState.value.state == StretchingRoutineState.RUNNING
        if (isRunning) {
            pause()
        } else {
            if (routineJob == null) {
                start()
            } else {
                routineJob?.resume()
                updateUiState {
                    copy(
                        state = StretchingRoutineState.RUNNING
                    )
                }
            }
        }
    }

    private fun start() {
        routineJob = stretchingRoutineRepository.getRoutine()
            .onEach { exercise ->
                updateUiState {
                    copy(
                        exercise = StringUiData.Resource(resource = exercise.nameRes)
                    )
                }
            }
            .flatMapConcat { it.steps }
            .onEach { step ->
                updateUiState {
                    copy(
                        step = StringUiData.Resource(resource = step.nameRes)
                    )
                }
            }
            .flatMapConcat { it.actions }
            .onEach { action ->
                when (action) {
                    is StepAction.Say -> {
                        textToSpeechManager.speak(action.text)
                    }
                    is StepAction.Wait -> {
                        delay(action.duration)
                    }
                    is StepAction.Beep -> {
                        beepToneManager.playBeep()
                    }
                    is StepAction.DoubleBeep -> {
                        beepToneManager.playDoubleBeep()
                    }
                }
            }
            .onStart {
                updateUiState {
                    copy(
                        state = StretchingRoutineState.RUNNING
                    )
                }
            }
            .onCompletion { a ->
                routineJob = null
                updateUiState {
                    copy(
                        state = StretchingRoutineState.IDLE
                    )
                }
            }
            .flowOn(Dispatchers.Default)
            .launchInPausing(viewModelScope)
    }

    private fun pause() {
        routineJob?.pause()
        updateUiState { copy(state = StretchingRoutineState.PAUSED) }
    }

    fun onStopClick() {
        routineJob?.cancel()
        routineJob = null
        updateUiState {
            copy(
                state = StretchingRoutineState.IDLE,
                exercise = StringUiData.Companion.Empty,
                step = StringUiData.Companion.Empty,
            )
        }
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
