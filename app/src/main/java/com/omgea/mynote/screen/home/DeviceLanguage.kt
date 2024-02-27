package com.omgea.mynote.screen.home

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.LocaleListCompat
import com.omgea.mynote.util.LocaleType

import java.util.Locale

@Composable
fun DeviceLanguage(locale: LocaleType) {
    val appLocale = when (locale) {
        LocaleType.English -> Locale.ENGLISH
        LocaleType.Myanmar -> Locale("my", "MM")
    }

    AppCompatDelegate.setApplicationLocales(
        LocaleListCompat.forLanguageTags(
            appLocale.toLanguageTag()
        )
    )
    val configuration = LocalConfiguration.current
    val resources = LocalContext.current.resources
    configuration.setLocale(appLocale)
    resources.updateConfiguration(configuration, resources.displayMetrics)
}
