package com.diagorus.nstretching.shared.util.audio

import platform.AudioToolbox.AudioServicesPlaySystemSound

class BeepToneManagerImpl: BeepToneManager {

    override suspend fun playBeep() {
        AudioServicesPlaySystemSound(1103.toUInt())
    }

    override suspend fun playDoubleBeep() {
        playBeep()
        playBeep()
    }
}