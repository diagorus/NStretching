package com.diagorus.nstretching.shared.stretching.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.diagorus.nstretching.shared.stretching.LocaleWithTextToSpeechAvailability
import com.diagorus.nstretching.shared.stretching.data.routine.state.RoutineState
import com.diagorus.nstretching.shared.stretching.data.routine.state.RoutineStatus
import com.diagorus.nstretching.shared.stretching.ui.viewModel.StretchingRoutineUiState
import com.diagorus.nstretching.shared.util.locale.StringUiData
import com.diagorus.nstretching.shared.util.locale.SupportedLocale
import com.diagorus.nstretching.shared.util.locale.LocaleWithName

@Preview
@Composable
private fun StretchingRoutineScreenPreview0() {
    MaterialTheme {
        StretchingRoutineScreen(
            StretchingRoutineUiState(
                supportedLocales = listOf(
                    LocaleWithTextToSpeechAvailability(
                        localeWithName = LocaleWithName.default,
                        isTextToSpeechAvailable = true,
                    ),
                    LocaleWithTextToSpeechAvailability(
                        localeWithName = LocaleWithName(SupportedLocale.GERMAN, StringUiData.Value("English")),
                        isTextToSpeechAvailable = false,
                    ),
                ),
                currentLocale = LocaleWithTextToSpeechAvailability(
                    localeWithName = LocaleWithName.default,
                    isTextToSpeechAvailable = true,
                ),
                routineState = RoutineState(
                    status = RoutineStatus.RUNNING,
                    exerciseName = StringUiData.Value("Hands"),
                    stepName = StringUiData.Value("Preparation"),
                ),
            ),
            StretchingRoutineCallbacks(
                onStartPauseClick = {},
                onStopClick = {},
                onLanguageClick = {},
                onEngineClick = {},
            )
        )
    }
}