package br.com.handt.helpers

import android.content.Context
import br.com.handt.dagger.ConstantsModule.Companion.ID_ONE
import com.onesignal.OneSignal
import javax.inject.Inject
import javax.inject.Named

class SignalSender @Inject constructor(
    @Named(ID_ONE) private val id: String
) {
    fun send(deep: String?, flyer: Flyer?, adId: String, c: Context) {
        OneSignal.initWithContext(c)
        OneSignal.setAppId(id)
        OneSignal.setExternalUserId(adId)

        when {
            flyer?.get("campaign") == null && deep == null -> {
                OneSignal.sendTag("key2", "organic")
            }
            deep != null -> {
                OneSignal.sendTag(
                    "key2",
                    deep.replace("myapp://", "").substringBefore("/")
                )
            }
            flyer?.get("campaign") != null -> {
                OneSignal.sendTag(
                    "key2",
                    flyer["campaign"].toString().substringBefore("_")
                )
            }
        }
    }
}