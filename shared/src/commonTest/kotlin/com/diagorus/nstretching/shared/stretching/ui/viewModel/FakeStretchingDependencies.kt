package com.diagorus.nstretching.shared.stretching.ui.viewModel

import com.diagorus.nstretching.shared.stretching.data.routine.StretchingRoutineRepository
import com.diagorus.nstretching.shared.stretching.data.routine.exercise.StretchingExercise
import com.diagorus.nstretching.shared.util.audio.BeepToneManager
import com.diagorus.nstretching.shared.util.locale.LocaleManager
import com.diagorus.nstretching.shared.util.locale.LocaleWithName
import com.diagorus.nstretching.shared.util.locale.StringUiData
import com.diagorus.nstretching.shared.util.locale.SupportedLocale
import com.diagorus.nstretching.shared.util.locale.transformToString
import com.diagorus.nstretching.shared.util.textToSpeech.TextToSpeechEngine
import com.diagorus.nstretching.shared.util.textToSpeech.TextToSpeechManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeBeepToneManager : BeepToneManager {
    var beepCount = 0
    var doubleBeepCount = 0

    override suspend fun playBeep() {
        beepCount++
    }

    override suspend fun playDoubleBeep() {
        doubleBeepCount++
    }
}

class FakeTextToSpeechManager : TextToSpeechManager {
    val spokenText = mutableListOf<String>()
    var isLanguageAvailable = true
    var currentEngine: TextToSpeechEngine? = null
    var engines = listOf<TextToSpeechEngine>()

    override suspend fun isLanguageAvailable(locale: LocaleWithName): Boolean {
        return isLanguageAvailable
    }

    override suspend fun speak(text: StringUiData) {
        spokenText.add(text.transformToString())
    }

    override suspend fun setEngine(engine: TextToSpeechEngine) {
        currentEngine = engine
    }

    override suspend fun getEngines(): List<TextToSpeechEngine> {
        return engines
    }

    override suspend fun getCurrentEngine(): TextToSpeechEngine? {
        return currentEngine
    }
}

class FakeLocaleManager : LocaleManager {
    var currentLocale: LocaleWithName = LocaleWithName(SupportedLocale.ENGLISH, StringUiData.Value("English"))
    override val supportedLocales: List<LocaleWithName> = listOf(currentLocale)

    override fun getCurrentLocale(): LocaleWithName {
        return currentLocale
    }

    override fun setLocale(localeWithName: LocaleWithName) {
        currentLocale = localeWithName
    }
}

class FakeStretchingRoutineRepository : StretchingRoutineRepository {
    var routineFlow: Flow<StretchingExercise> = flowOf()

    override fun getRoutine(): Flow<StretchingExercise> {
        return routineFlow
    }
}
