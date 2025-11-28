package com.diagorus.nstretching.shared.util.textToSpeech

import android.content.Context
import android.speech.tts.TextToSpeech
import co.touchlab.kermit.Logger
import com.diagorus.nstretching.shared.util.locale.LocaleManager
import com.diagorus.nstretching.shared.util.preferences.PreferencesDataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class TextToSpeechInitializationManager(
    private val applicationContext: Context,
    private val applicationScope: CoroutineScope,
    private val localeManager: LocaleManager,
    private val preferencesDataStoreManager: PreferencesDataStoreManager,
    private val utteranceManager: UtteranceManager,
) {

    private var ttsDeferred: Deferred<TextToSpeech> = initAsync()

    private fun initAsync() = applicationScope.async {
        init()
    }

    private suspend fun init(): TextToSpeech {
        val engine = preferencesDataStoreManager.chosenTextToSpeechEnginePackageFlow.firstOrNull()
        return suspendCancellableCoroutine<TextToSpeech> { continuation ->
            SelfReference.create<TextToSpeech> {
                TextToSpeech(
                    applicationContext,
                    { status ->
                        if (status == TextToSpeech.SUCCESS) {
                            val currentLocale = localeManager.getCurrentLocale()
                            val languageStatus = self.setLanguage(Locale(currentLocale.supportedLocale.tag))
                            when (languageStatus) {
                                TextToSpeech.LANG_AVAILABLE,
                                TextToSpeech.LANG_COUNTRY_AVAILABLE,
                                TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE -> {
                                    self.setOnUtteranceProgressListener(utteranceManager)
                                }
                                else -> {
                                    Logger.e { "Language not supported: $languageStatus" }
                                }
                            }
                            continuation.resume(self)
                        } else {
                            continuation.resumeWithException(IllegalStateException("TextToSpeech initialization failed, code $status"))
                        }
                    },
                    engine
                )
            }
        }
    }

    suspend fun reinit() {
        val tts = ttsDeferred.await()
        tts.shutdown()
        ttsDeferred = initAsync()
    }

    suspend fun getInstance(): TextToSpeech {
        return ttsDeferred.await()
    }
}