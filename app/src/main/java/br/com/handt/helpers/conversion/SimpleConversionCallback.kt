package br.com.handt.helpers.conversion

import br.com.handt.helpers.Flyer

class SimpleConversionCallback(
    private val callback: (Flyer?) -> Unit
) : SimpleConversionListener {
    override fun onConversionDataSuccess(data: MutableMap<String, Any>?) = callback(data)
    override fun onConversionDataFail(p0: String?) = callback(null)
}