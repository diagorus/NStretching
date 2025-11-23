package com.diagorus.nstretching.shared.util.textToSpeech

import com.diagorus.nstretching.shared.util.locale.LocaleManager
import kotlinx.cinterop.ObjCSignatureOverride
import platform.AVFAudio.AVSpeechSynthesisVoice
import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechSynthesizerDelegateProtocol
import platform.AVFAudio.AVSpeechUtterance
import platform.darwin.NSObject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class UtteranceManager(
    val localeManager: LocaleManager,
) : NSObject(), AVSpeechSynthesizerDelegateProtocol {

    private val utteranceData = HashMap<String, UtteranceData>()

    fun create(text: String, continuation: Continuation<Unit>): UtteranceData {
        val currentLocaleTag = localeManager.getCurrentLocale().supportedLocale.tag
        val utterance = AVSpeechUtterance(string = text).apply {
            voice = AVSpeechSynthesisVoice.Companion.voiceWithLanguage(currentLocaleTag)
        }
        val data  = UtteranceData(utterance, continuation)
        utteranceData[utterance.getId()] = data
        return data
    }

    private fun AVSpeechUtterance.getId(): String {
        return hash.toString()
    }

    @ObjCSignatureOverride
    override fun speechSynthesizer(
        synthesizer: AVSpeechSynthesizer,
        didStartSpeechUtterance: AVSpeechUtterance
    ) {
        val id = didStartSpeechUtterance.getId()
        val started = utteranceData.remove(id) as UtteranceData
        utteranceData.values.forEach { flushed ->
            flushed.continuation.resumeWithException(Exception("UTTERANCE_FLUSH"))
        }
        utteranceData.clear()

        utteranceData[id] = started
    }

    @ObjCSignatureOverride
    override fun speechSynthesizer(
        synthesizer: AVSpeechSynthesizer,
        didFinishSpeechUtterance: AVSpeechUtterance,
    ) {
        val id = didFinishSpeechUtterance.getId()
        val utterance = utteranceData.remove(id) as UtteranceData
        utterance.continuation.resume(Unit)
    }
}