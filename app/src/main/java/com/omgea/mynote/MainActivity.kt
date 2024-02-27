package com.omgea.mynote

import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.omgea.mynote.graph.MainScreen
import com.omgea.mynote.ui.theme.MyNoteTheme
import com.omgea.mynote.util.LocaleType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val vm: MainViewModel by viewModels()
    @ExperimentalAnimationApi
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            observeAppLocale()
        }
        setContent {
            MyNoteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    content = {
                        val navController = rememberNavController()
                        MainScreen(navController = navController)
                    }
                )
            }
        }
    }


   @RequiresApi(Build.VERSION_CODES.N)
   private suspend fun observeAppLocale() {
        vm.appLocale.collectLatest {
            val appLocale = when (it) {
                LocaleType.English -> Locale.ENGLISH
                LocaleType.Myanmar -> Locale("my", "MM")
            }
            setLocale(appLocale)
        }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun setLocale(locale: Locale) {
        val resources = resources
        val configuration = resources.configuration
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(
                locale.language,
            ),
        )
        val localeList = LocaleList(locale)
        LocaleList.setDefault(localeList)
        configuration.setLocales(localeList)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}