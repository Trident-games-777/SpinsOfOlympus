package br.com.handt.helpers

import android.view.View

data class FunState(
    val text: String,
    val picture1: Int,
    val picture2: Int,
    val picture3: Int,
    val spinVisible: Int = View.VISIBLE,
    val spinButtonEnabled: Boolean = true,
    val exitVisible: Int = View.INVISIBLE,
    val retryVisible: Int = View.INVISIBLE
)
