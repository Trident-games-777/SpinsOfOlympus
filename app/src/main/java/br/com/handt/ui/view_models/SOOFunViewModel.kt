package br.com.handt.ui.view_models

import android.view.View
import androidx.lifecycle.ViewModel
import br.com.handt.R
import br.com.handt.helpers.FunState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SOOFunViewModel : ViewModel() {
    private var coins = START_AMOUNT
    private val pictures = listOf(
        R.drawable.soo_item1, R.drawable.soo_item2,
        R.drawable.soo_item3, R.drawable.soo_item4, R.drawable.soo_item5
    )
    private var currentRow: List<Int> = emptyList()

    private val _funState: MutableStateFlow<FunState> = MutableStateFlow(
        FunState(
            text = "Your coins: $coins",
            picture1 = EMPTY, picture2 = EMPTY, picture3 = EMPTY
        )
    )
    val funState: StateFlow<FunState> = _funState.asStateFlow()

    private val _actionSpin: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val actionSpin: StateFlow<Boolean> = _actionSpin.asStateFlow()

    fun spin() {
        currentRow = buildList {
            add(pictures.shuffled().first())
            add(pictures.shuffled().first())
            add(pictures.shuffled().first())
        }
        coins--
        if (currentRow.all { it == currentRow.first() }) coins += 10
        _funState.update { state ->
            state.copy(
                text = if (coins > 0) "Your coins: $coins" else "You have no coins",
                picture1 = currentRow[0],
                picture2 = currentRow[1],
                picture3 = currentRow[2],
                spinVisible = if (coins > 0) View.VISIBLE else View.INVISIBLE,
                spinButtonEnabled = false,
                retryVisible = if (coins > 0) View.INVISIBLE else View.VISIBLE,
                exitVisible = if (coins > 0) View.INVISIBLE else View.VISIBLE
            )
        }
        _actionSpin.value = !_actionSpin.value
    }

    fun animationEnd() = _funState.update { state -> state.copy(spinButtonEnabled = true) }

    companion object {
        const val EMPTY = -1
        private const val START_AMOUNT = 30
    }
}