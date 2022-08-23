package br.com.handt.helpers

import android.content.Context
import android.provider.Settings
import java.io.File

class RootChecker(
    context: Context
) {
    val secured by lazy { isSecureADB && isSecureFS }

    private val isSecureADB by lazy {
        Settings.Global.getString(context.contentResolver, Settings.Global.ADB_ENABLED) != "1"
    }

    private val isSecureFS by lazy {
        try {
            val paths = setOf(
                "/sbin/su",
                "/system/bin/su",
                "/system/xbin/su",
                "/data/local/xbin/su",
                "/data/local/bin/su",
                "/system/sd/xbin/su",
                "/system/bin/failsafe/su",
                "/data/local/su"
            )
            var result = true
            paths.forEach { result = result && !File(it).exists() }
            result
        } catch (e: SecurityException) {
            true
        }
    }
}