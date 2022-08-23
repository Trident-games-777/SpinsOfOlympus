package br.com.handt

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.handt.dagger.ConstantsModule.Companion.BASE
import javax.inject.Inject
import javax.inject.Named

class PreferencesRepository @Inject constructor(
    private val preferences: SharedPreferences,
    @Named(BASE) private val base: String
) {
    fun getPreferencesString(): String? {
        return preferences.getString(STR, null)
    }

    fun setPreferencesString(str: String) {
        if (getPreferencesString() == null && base !in str) {
            preferences.edit {
                putString(STR, str)
                apply()
            }
        }
    }

    companion object {
        private const val STR = "str"
    }
}