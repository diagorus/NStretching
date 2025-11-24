package com.diagorus.nstretching.shared.util.textToSpeech.auxiliaries

import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import org.koin.core.annotation.Single
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Single
class UtteranceManager : UtteranceProgressListener() {

    private val utterances = HashMap<String, Utterance>()
    private var nextUtteranceId = 0L

    @Synchronized
    fun create(value: String, queueMode: Int, continuation: Continuation<Unit>): Utterance {
        val utteranceId = generateUtteranceId()
        val utterance = Utterance(utteranceId, value, queueMode, continuation)
        utterances[utteranceId] = utterance
        return utterance
    }

    private fun generateUtteranceId(): String {
        val utteranceId = nextUtteranceId++
        return utteranceId.toString()
    }

    @Synchronized
    override fun onStart(utteranceId: String) {
        val started = utterances.remove(utteranceId) as Utterance
        if (started.queueMode == TextToSpeech.QUEUE_FLUSH) {
            utterances.values.forEach { flushed ->
                flushed.continuation.resumeWithException(Exception("UTTERANCE_FLUSH"))
            }
            utterances.clear()
        }
        utterances[utteranceId] = started
    }

    @Synchronized
    override fun onDone(utteranceId: String) {
        val utterance = utterances.remove(utteranceId) as Utterance
        utterance.continuation.resume(Unit)
    }

    @Synchronized
    override fun onError(utteranceId: String) {
        val utterance = utterances.remove(utteranceId) as Utterance
        utterance.continuation.resumeWithException(Exception("UTTERANCE_ERROR"))
    }
}