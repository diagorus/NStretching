package com.diagorus.nstretching.shared.util.textToSpeech

import com.diagorus.nstretching.shared.util.locale.LocaleManager
import com.diagorus.nstretching.shared.util.locale.StringUiData
import com.diagorus.nstretching.shared.util.locale.LocaleWithName
import com.diagorus.nstretching.shared.util.locale.transformToString
import com.diagorus.nstretching.shared.util.textToSpeech.UtteranceManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.AVFAudio.AVSpeechSynthesisVoice
import platform.AVFAudio.AVSpeechSynthesizer
import kotlin.coroutines.suspendCoroutine

actual class TextToSpeechManager(
    val defaultDispatcher: CoroutineDispatcher,
    localeManager: LocaleManager,
) {

    // creation through Koin crashes
    private val utteranceManager = UtteranceManager(localeManager)

    private val synthesizer = AVSpeechSynthesizer().apply {
        delegate = utteranceManager
    }

    actual suspend fun isLanguageAvailable(locale: LocaleWithName): Boolean {
        val voices = AVSpeechSynthesisVoice.speechVoices()
        return voices.contains(AVSpeechSynthesisVoice.voiceWithLanguage(locale.supportedLocale.tag))
    }

    actual suspend fun getEngines(): List<TextToSpeechEngine> {
        return emptyList()
    }

    actual suspend fun getCurrentEngine(): TextToSpeechEngine? {
        return null
    }

    actual suspend fun setEngine(engine: TextToSpeechEngine) {
        // do nothing
    }

    actual suspend fun speak(text: StringUiData) = withContext(Dispatchers.Default) {
        val localisedText = text.transformToString()
        suspendCoroutine { continuation ->
            val utteranceData = utteranceManager.create(localisedText, continuation)
            synthesizer.speakUtterance(utteranceData.utterance)
        }
    }
}