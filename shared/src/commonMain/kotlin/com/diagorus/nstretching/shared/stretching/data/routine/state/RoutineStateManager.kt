package com.diagorus.nstretching.shared.stretching.data.routine.state

import co.touchlab.kermit.Logger
import com.diagorus.nstretching.shared.stretching.data.routine.StretchingRoutineRepository
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.step.actions.StepAction
import com.diagorus.nstretching.shared.util.NO_VALUE
import com.diagorus.nstretching.shared.util.audio.BeepToneManager
import com.diagorus.nstretching.shared.util.locale.StringUiData
import com.diagorus.nstretching.shared.util.locale.transformToString
import com.diagorus.nstretching.shared.util.textToSpeech.TextToSpeechManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.supervisorScope

class RoutineStateManager(
    private val stretchingRoutineRepository: StretchingRoutineRepository,
    private val textToSpeechManager: TextToSpeechManager,
    private val beepToneManager: BeepToneManager,
    private val defaultDispatcher: CoroutineDispatcher,
) {

    private val _state = MutableStateFlow(RoutineState())
    val state = _state.asStateFlow()

    private var routineJob: Job? = null

    private var currentExerciseIndex: Int = NO_VALUE
    private var currentStepIndex: Int = NO_VALUE
    private var currentActionIndex: Int = NO_VALUE

    suspend fun startOrPause() {
        val isRunning = _state.value.status == RoutineStatus.RUNNING
        if (isRunning) {
            pause()
        } else {
            start()
        }
    }

    private suspend fun start() = supervisorScope {
        Logger.e { ">>> start" }

        routineJob = stretchingRoutineRepository.getRoutine()
            .withIndex()
            .dropWhile { (index, _) ->
                currentExerciseIndex != NO_VALUE && currentExerciseIndex > index
            }
            .onEach { (index, exercise) ->
                currentExerciseIndex = index

                Logger.e { ">>> onEach exercise:${StringUiData.Resource(resource = exercise.nameRes).transformToString()} currentExerciseIndex:${currentExerciseIndex}" }
                _state.update {
                    it.copy(
                        exerciseName = StringUiData.Resource(resource = exercise.nameRes)
                    )
                }
            }
            .map { it.value }
            .flatMapConcat { it.steps }
            .withIndex()
            .dropWhile { (index, _) ->
                currentStepIndex != NO_VALUE && currentStepIndex > index
            }
            .onEach { (index, step) ->
                currentStepIndex = index - currentExerciseIndex

                Logger.e { ">>> onEach step:${StringUiData.Resource(resource = step.nameRes).transformToString()} currentExerciseIndex:${currentExerciseIndex} currentStepIndex:${currentStepIndex}" }

                _state.update {
                    it.copy(
                        stepName = StringUiData.Resource(resource = step.nameRes)
                    )
                }
            }
            .map { it.value }
            .flatMapConcat { it.actions }
            .withIndex()
            .dropWhile { (index, _) ->
                currentActionIndex != NO_VALUE && currentActionIndex > index
            }
            .onEach { (index, action) ->
                currentActionIndex = index - currentExerciseIndex - currentStepIndex

                Logger.e { ">>> onEach action:${action} currentExerciseIndex:${currentExerciseIndex} currentStepIndex:${currentStepIndex} currentActionIndex:${currentActionIndex}" }

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
            .map { it.value }
            .onStart {
                _state.update {
                    it.copy(
                        status = RoutineStatus.RUNNING,
                    )
                }
            }
            .onCompletion {
                if (state.value.status != RoutineStatus.PAUSED) {
                    stop()
                }
            }
            .flowOn(defaultDispatcher)
            .launchIn(this)
    }

    private fun pause() {
        Logger.e { ">>> Pause" }
        _state.update {
            it.copy(
                status = RoutineStatus.PAUSED,
            )
        }

        routineJob?.cancel()
        routineJob = null
    }

    fun stop() {
        Logger.e { ">>> Stop" }

        _state.update {
            it.copy(
                status = RoutineStatus.IDLE,
                exerciseName = StringUiData.Empty,
                stepName = StringUiData.Empty,
            )
        }

        currentExerciseIndex = NO_VALUE
        currentStepIndex = NO_VALUE
        currentActionIndex = NO_VALUE

        routineJob?.cancel()
        routineJob = null
    }
}