package br.com.handt.helpers

import android.content.Context
import androidx.core.net.toUri
import br.com.handt.R
import br.com.handt.dagger.ConstantsModule
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class UrlMaker @Inject constructor(
    @Named(ConstantsModule.BASE) private val base: String
) {
    fun make(deep: String?, flyer: Flyer?, uid: String?, adId: String, c: Context): String {
        return "https://${base}".toUri().buildUpon().apply {
            appendQueryParameter(
                c.resources.getString(R.string.secure_get_parametr),
                c.resources.getString(R.string.secure_key)
            )
            appendQueryParameter(
                c.resources.getString(R.string.dev_tmz_key),
                TimeZone.getDefault().id
            )
            appendQueryParameter(c.resources.getString(R.string.gadid_key), adId)
            appendQueryParameter(c.resources.getString(R.string.deeplink_key), deep)
            appendQueryParameter(
                c.resources.getString(R.string.source_key),
                if (deep != null) "deeplink" else flyer?.get("media_source").toString()
            )
            appendQueryParameter(
                c.resources.getString(R.string.af_id_key), if (deep != null) "null" else uid.toString()
            )
            appendQueryParameter(
                c.resources.getString(R.string.adset_id_key),
                flyer?.get("adset_id").toString()
            )
            appendQueryParameter(
                c.resources.getString(R.string.campaign_id_key),
                flyer?.get("campaign_id").toString()
            )
            appendQueryParameter(
                c.resources.getString(R.string.app_campaign_key),
                flyer?.get("campaign").toString()
            )
            appendQueryParameter(
                c.resources.getString(R.string.adset_key),
                flyer?.get("adset").toString()
            )
            appendQueryParameter(
                c.resources.getString(R.string.adgroup_key),
                flyer?.get("adgroup").toString()
            )
            appendQueryParameter(
                c.resources.getString(R.string.orig_cost_key),
                flyer?.get("orig_cost").toString()
            )
            appendQueryParameter(
                c.resources.getString(R.string.af_siteid_key),
                flyer?.get("af_siteid").toString()
            )
        }.toString()
    }
}