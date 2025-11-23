package com.diagorus.nstretching.shared.util.textToSpeech

import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.qualifier.StringQualifier
import org.koin.core.scope.Scope

@Module
@Configuration
actual class TextToSpeechManagerMainModule {

    @Single
    actual fun provideTextToSpeechManager(scope: Scope): TextToSpeechManager {
        return TextToSpeechManager(
            // generated classes transform named annotations into StringQualifier; drawback of dynamic injection
            scope.get(StringQualifier("com.diagorus.nstretching.shared.util.koin.CoroutinesModule.DefaultDispatcher")),
            scope.get(),
        )
    }
}