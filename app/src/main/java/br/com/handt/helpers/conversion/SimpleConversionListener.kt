package br.com.handt.helpers.conversion

import com.appsflyer.AppsFlyerConversionListener

interface SimpleConversionListener : AppsFlyerConversionListener {
    override fun onAppOpenAttribution(p0: MutableMap<String, String>?) = Unit
    override fun onAttributionFailure(p0: String?) = Unit
}