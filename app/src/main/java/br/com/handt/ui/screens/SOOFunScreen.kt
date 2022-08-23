package br.com.handt.ui.screens

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.handt.databinding.SsoFunScreenBinding
import br.com.handt.helpers.FunState
import br.com.handt.ui.view_models.SOOFunViewModel
import br.com.handt.ui.view_models.SOOFunViewModel.Companion.EMPTY
import kotlinx.coroutines.launch

class SOOFunScreen : AppCompatActivity() {
    private lateinit var funViewModel: SOOFunViewModel
    private lateinit var funScreenBinding: SsoFunScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        funScreenBinding = SsoFunScreenBinding.inflate(layoutInflater)
        setContentView(funScreenBinding.root)
        funViewModel = ViewModelProvider(this)[SOOFunViewModel::class.java]

        lifecycleScope.launch {
            funViewModel.funState.collect { state ->
                renderView(state)
            }
        }
        lifecycleScope.launch {
            funViewModel.actionSpin.collect {
                animate()
            }
        }

        funScreenBinding.buttonSpin.setOnClickListener { funViewModel.spin() }
        funScreenBinding.buttonRetry.setOnClickListener {
            startActivity(Intent(this, SOOFunScreen::class.java))
            finish()
        }
        funScreenBinding.buttonExit.setOnClickListener { finish() }
    }

    private fun renderView(state: FunState) {
        funScreenBinding.textViewCoinsCount.text = state.text
        if (state.picture1 != EMPTY) funScreenBinding.first.setImageResource(state.picture1)
        if (state.picture2 != EMPTY) funScreenBinding.second.setImageResource(state.picture2)
        if (state.picture3 != EMPTY) funScreenBinding.third.setImageResource(state.picture3)
        funScreenBinding.buttonSpin.visibility = state.spinVisible
        funScreenBinding.buttonSpin.isEnabled = state.spinButtonEnabled
        funScreenBinding.buttonRetry.visibility = state.retryVisible
        funScreenBinding.buttonExit.visibility = state.exitVisible
    }

    private fun animate() =
        ObjectAnimator.ofFloat(funScreenBinding.ll, View.TRANSLATION_Y, -700f, 0f).apply {
            interpolator = BounceInterpolator()
            doOnEnd {
                funViewModel.animationEnd()
            }
            start()
        }
}