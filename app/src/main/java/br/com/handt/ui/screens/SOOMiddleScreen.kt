package br.com.handt.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.handt.R
import br.com.handt.appComponent
import br.com.handt.ui.screens.SOOEndScreen.Companion.SOO_LINK
import br.com.handt.ui.view_models.SOOViewModel
import br.com.handt.ui.view_models.SOOViewModelFactory
import javax.inject.Inject

class SOOMiddleScreen : AppCompatActivity() {

    @Inject
    lateinit var sooFactory: SOOViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sso_middle_screen)
        appComponent.inject(this)
        val sooViewModel = ViewModelProvider(this, sooFactory)[SOOViewModel::class.java]
        sooViewModel.loadResult(this)
        sooViewModel.result.observe(this) { result ->
            val sooIntent = Intent(this, SOOEndScreen::class.java)
            sooIntent.putExtra(SOO_LINK, result)
            startActivity(sooIntent)
            finish()
        }
    }
}