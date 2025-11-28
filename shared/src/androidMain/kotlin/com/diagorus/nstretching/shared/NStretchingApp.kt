package com.diagorus.nstretching.shared

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import co.touchlab.kermit.Logger
import com.diagorus.nstretching.shared.util.koin.initKoin
import com.diagorus.nstretching.shared.util.locale.LocaleManager
import com.diagorus.nstretching.shared.util.locale.LocaleManagerImpl
import com.diagorus.nstretching.shared.util.textToSpeech.TextToSpeechManager
import com.diagorus.nstretching.shared.util.textToSpeech.TextToSpeechManagerImpl
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class NStretchingApp : Application() {

//    private val textToSpeechManager by inject<TextToSpeechManagerImpl>()
//    private val localeManager by inject<LocaleManagerImpl>()

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@NStretchingApp)
            androidLogger()
        }

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                if (intent.action == Intent.ACTION_LOCALE_CHANGED) {
//                    textToSpeechManager.onLanguageChanged()
//                    localeManager.onLocaleChanged()
                }
            }
        }
        registerReceiver(receiver, IntentFilter(Intent.ACTION_LOCALE_CHANGED))
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(ContextCompat.getContextForLanguage(base))
    }
}