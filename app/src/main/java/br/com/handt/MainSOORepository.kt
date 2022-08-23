package br.com.handt

import android.content.Context
import br.com.handt.dagger.ConstantsModule.Companion.ID_FL
import br.com.handt.helpers.Flyer
import br.com.handt.helpers.conversion.SimpleConversionCallback
import com.appsflyer.AppsFlyerLib
import com.facebook.applinks.AppLinkData
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainSOORepository @Inject constructor(
    @Named(ID_FL) private val id_fl: String
) {
    suspend fun deep(c: Context): String? = suspendCoroutine { cont ->
        AppLinkData.fetchDeferredAppLinkData(c) { appLinkData ->
            appLinkData?.targetUri?.let { cont.resume(it.toString()) } ?: cont.resume(null)
        }
    }

    suspend fun flyer(c: Context): Flyer? = suspendCoroutine { cont ->
        val listener = SimpleConversionCallback { flyer -> cont.resume(flyer) }
        AppsFlyerLib.getInstance().init(id_fl, listener, c)
        AppsFlyerLib.getInstance().start(c)
    }

    fun googleId(c: Context): String = AdvertisingIdClient.getAdvertisingIdInfo(c).id.toString()

    fun flyerUID(c: Context): String = AppsFlyerLib.getInstance().getAppsFlyerUID(c)
}