package br.com.handt.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.handt.appComponent
import br.com.handt.helpers.RootChecker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SOOStartScreen : AppCompatActivity() {
    @Inject
    lateinit var rootChecker: RootChecker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        lifecycleScope.launch {
            delay(1000)
            if (rootChecker.secured) {
                startActivity(Intent(this@SOOStartScreen, SOOMiddleScreen::class.java))
            } else {
                startActivity(Intent(this@SOOStartScreen, SOOFunScreen::class.java))
            }
            finish()
        }
    }
}