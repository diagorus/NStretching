package com.diagorus.nstretching.shared.util.audio

interface BeepToneManager {
    suspend fun playBeep()
    suspend fun playDoubleBeep()
}