package br.com.handt.ui.view_models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.handt.MainSOORepository
import br.com.handt.PreferencesRepository
import br.com.handt.helpers.SignalSender
import br.com.handt.helpers.UrlMaker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SOOViewModel(
    private val preferencesRepository: PreferencesRepository,
    private val mainSOORepository: MainSOORepository,
    private val urlMaker: UrlMaker,
    private val signalSender: SignalSender
) : ViewModel() {

    private var _result: MutableLiveData<String> = MutableLiveData()
    val result: LiveData<String> = _result

    fun loadResult(context: Context) {
        preferencesRepository.getPreferencesString()?.let {
            _result.postValue(it)
        } ?: run {
            viewModelScope.launch(Dispatchers.IO) {
                val deep = mainSOORepository.deep(context)
                val googleId = mainSOORepository.googleId(context)
                deep?.let { nonNullableDeep ->
                    val res = urlMaker.make(
                        deep = nonNullableDeep,
                        flyer = null,
                        uid = null,
                        adId = googleId,
                        context
                    )
                    signalSender.send(
                        deep = nonNullableDeep,
                        flyer = null,
                        adId = googleId,
                        context
                    )
                    _result.postValue(res)
                } ?: run {
                    val flyer = mainSOORepository.flyer(context)
                    val res = urlMaker.make(
                        deep = null,
                        flyer = flyer,
                        uid = mainSOORepository.flyerUID(context),
                        adId = mainSOORepository.googleId(context),
                        context
                    )
                    signalSender.send(
                        deep = null,
                        flyer = flyer,
                        adId = googleId,
                        context
                    )
                    _result.postValue(res)
                }
            }
        }
    }
}