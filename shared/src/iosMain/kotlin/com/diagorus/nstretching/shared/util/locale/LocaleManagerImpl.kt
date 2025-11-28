package com.diagorus.nstretching.shared.util.locale

import com.diagorus.nstretching.shared.util.environment.customAppLocale
import platform.Foundation.NSLocale
import platform.Foundation.localizedStringForLanguageCode
import platform.Foundation.preferredLanguages

class LocaleManagerImpl : LocaleManager {

    override val supportedLocales: List<LocaleWithName> by lazy {
        SupportedLocale.entries
            .mapNotNull { supportedLocale ->
                val displayName = supportedLocale.getDisplayName()
                displayName?.let {
                    LocaleWithName(supportedLocale, displayName)
                }
            }
    }

    private fun SupportedLocale.getDisplayName(): StringUiData? {
        val locale = NSLocale(tag)
        val displayName = locale.localizedStringForLanguageCode(tag)?.capitalize()
        return displayName?.let {
            StringUiData.Value(it)
        }
    }

    override fun getCurrentLocale(): LocaleWithName {
        val languageTag = NSLocale.preferredLanguages.firstOrNull()?.toString()
        val supportedLocale = SupportedLocale.fromLanguageTag(languageTag)
        val displayName = supportedLocale.getDisplayName()
        return if (displayName == null) {
            LocaleWithName.default
        } else {
            LocaleWithName(supportedLocale, displayName)
        }
    }

    override fun setLocale(locale: LocaleWithName) {
        customAppLocale = locale.supportedLocale.tag
    }
}