package com.diagorus.nstretching.shared.util.locale

interface LocaleManager {

    val supportedLocales: List<LocaleWithName>

    fun getCurrentLocale(): LocaleWithName
    fun setLocale(locale: LocaleWithName)
}