package com.diagorus.nstretching.shared.util.textToSpeech

import android.speech.tts.TextToSpeech
import com.diagorus.nstretching.shared.util.preferences.PreferencesDataStoreManager
import com.diagorus.nstretching.shared.util.locale.StringUiData
import com.diagorus.nstretching.shared.util.locale.LocaleWithName
import com.diagorus.nstretching.shared.util.locale.transformToString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.coroutines.suspendCoroutine

class TextToSpeechManagerImpl(
    private val applicationScope: CoroutineScope,
    private val textToSpeechInitializationManager: TextToSpeechInitializationManager,
    private val preferencesDataStoreManager: PreferencesDataStoreManager,
    private val utteranceManager: UtteranceManager,
): TextToSpeechManager {

    override suspend fun isLanguageAvailable(locale: LocaleWithName): Boolean {
        val textToSpeech = textToSpeechInitializationManager.getInstance()
        val languageStatus = textToSpeech.isLanguageAvailable(Locale(locale.supportedLocale.tag))
        return languageStatus != TextToSpeech.LANG_MISSING_DATA &&
                languageStatus != TextToSpeech.LANG_NOT_SUPPORTED
    }

    override suspend fun getCurrentEngine(): TextToSpeechEngine? {
        val textToSpeech = textToSpeechInitializationManager.getInstance()
        val currentEngine = preferencesDataStoreManager.chosenTextToSpeechEnginePackageFlow.firstOrNull()
            ?: textToSpeech.defaultEngine
        return getEngines().find { it.pkg == currentEngine }
    }

    override suspend fun getEngines(): List<TextToSpeechEngine> {
        val textToSpeech = textToSpeechInitializationManager.getInstance()
        return textToSpeech.engines.map { TextToSpeechEngine(it.name, it.label) }
    }

    override suspend fun setEngine(engine: TextToSpeechEngine) {
        preferencesDataStoreManager.saveChosenTextToSpeechEnginePackage(engine.pkg)
        textToSpeechInitializationManager.reinit()
    }

    override suspend fun speak(text: StringUiData) {
        val tts = textToSpeechInitializationManager.getInstance()
        val localisedText = text.transformToString()
        suspendCoroutine { continuation ->
            val utterance = utteranceManager.create(localisedText, TextToSpeech.QUEUE_FLUSH, continuation)
            tts.speak(utterance.text, utterance.queueMode, null, utterance.id)
        }
    }

    fun onLanguageChanged() {
        applicationScope.launch {
            textToSpeechInitializationManager.reinit()
        }
    }
}