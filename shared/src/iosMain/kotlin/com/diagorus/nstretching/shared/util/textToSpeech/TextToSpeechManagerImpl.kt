package com.diagorus.nstretching.shared.util.textToSpeech

import com.diagorus.nstretching.shared.util.locale.LocaleManager
import com.diagorus.nstretching.shared.util.locale.LocaleWithName
import com.diagorus.nstretching.shared.util.locale.StringUiData
import com.diagorus.nstretching.shared.util.locale.transformToString
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import platform.AVFAudio.AVSpeechSynthesisVoice
import platform.AVFAudio.AVSpeechSynthesizer
import kotlin.coroutines.suspendCoroutine

class TextToSpeechManagerImpl(
    private val defaultDispatcher: CoroutineDispatcher,
    localeManager: LocaleManager,
) : TextToSpeechManager {

    // crash when creating through Koin
    private val utteranceManager = UtteranceManager(localeManager)

    private val synthesizer = AVSpeechSynthesizer().apply {
        delegate = utteranceManager
    }

    override suspend fun isLanguageAvailable(locale: LocaleWithName): Boolean {
        val voices = AVSpeechSynthesisVoice.speechVoices()
        return voices.contains(AVSpeechSynthesisVoice.voiceWithLanguage(locale.supportedLocale.tag))
    }

    override suspend fun getEngines(): List<TextToSpeechEngine> {
        return emptyList()
    }

    override suspend fun getCurrentEngine(): TextToSpeechEngine? {
        return null
    }

    override suspend fun setEngine(engine: TextToSpeechEngine) {
        // do nothing
    }

    override suspend fun speak(text: StringUiData) = withContext(defaultDispatcher) {
        val localisedText = text.transformToString()
        suspendCoroutine { continuation ->
            val utteranceData = utteranceManager.create(localisedText, continuation)
            synthesizer.speakUtterance(utteranceData.utterance)
        }
    }
}