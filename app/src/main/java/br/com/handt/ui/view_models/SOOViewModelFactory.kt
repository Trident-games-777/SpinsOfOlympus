package br.com.handt.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.handt.MainSOORepository
import br.com.handt.PreferencesRepository
import br.com.handt.helpers.SignalSender
import br.com.handt.helpers.UrlMaker
import javax.inject.Inject

class SOOViewModelFactory @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val mainSOORepository: MainSOORepository,
    private val urlMaker: UrlMaker,
    private val signalSender: SignalSender
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SOOViewModel(preferencesRepository, mainSOORepository, urlMaker, signalSender) as T
    }
}