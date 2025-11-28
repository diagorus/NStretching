package com.diagorus.nstretching.shared.util.audio

import android.media.AudioManager
import android.media.ToneGenerator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class BeepToneManagerImpl(
    private val defaultDispatcher: CoroutineDispatcher,
) : BeepToneManager {

    private val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, TONE_VOLUME)

    override suspend fun playBeep() {
        play(ToneGenerator.TONE_PROP_BEEP)
    }

    private suspend fun play(toneType: Int) {
        withContext(defaultDispatcher) {
            toneGenerator.startTone(toneType)
        }
    }

    override suspend fun playDoubleBeep() {
        play(ToneGenerator.TONE_PROP_BEEP2)
    }

    companion object {
        private const val TONE_VOLUME = 100
    }
}