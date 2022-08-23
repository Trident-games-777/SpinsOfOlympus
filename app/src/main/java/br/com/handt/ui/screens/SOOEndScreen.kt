package br.com.handt.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.com.handt.PreferencesRepository
import br.com.handt.R
import br.com.handt.appComponent
import br.com.handt.dagger.ConstantsModule.Companion.BASE_SAFE
import javax.inject.Inject
import javax.inject.Named

class SOOEndScreen : AppCompatActivity() {
    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    @Inject
    @Named(BASE_SAFE)
    lateinit var baseSafe: String

    private lateinit var sooWebView: WebView
    private lateinit var chooserCallback: ValueCallback<Array<Uri?>>

    val getContent = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        chooserCallback.onReceiveValue(it.toTypedArray())
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sso_end_screen)
        appComponent.inject(this)

        window.statusBarColor = getColor(R.color.black)
        sooWebView = findViewById(R.id.sooWeb)

        sooWebView.loadUrl(intent.getStringExtra(SOO_LINK)!!)

        with(sooWebView.settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = false
            userAgentString = System.getProperty(STRING_AGENT)
        }

        with(CookieManager.getInstance()) {
            setAcceptCookie(true)
            setAcceptThirdPartyCookies(sooWebView, true)
        }

        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (sooWebView.canGoBack()) {
                        sooWebView.goBack()
                    } else {
                        finish()
                    }
                }
            })

        sooWebView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                super.onReceivedError(view, request, error)
                Log.e("TAG", error.description.toString())
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                CookieManager.getInstance().flush()
                if (url == baseSafe) {
                    startActivity(
                        Intent(this@SOOEndScreen, SOOFunScreen::class.java)
                    )
                    finish()
                } else {
                    preferencesRepository.setPreferencesString(url)
                }
            }
        }

        sooWebView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView,
                filePathCallback: ValueCallback<Array<Uri?>>,
                fileChooserParams: FileChooserParams
            ): Boolean {
                chooserCallback = filePathCallback
                getContent.launch(IMAGE_MIME_TYPE)
                return true
            }

            @SuppressLint("SetJavaScriptEnabled")
            override fun onCreateWindow(
                view: WebView?, isDialog: Boolean,
                isUserGesture: Boolean, resultMsg: Message
            ): Boolean {
                val newWebView = WebView(this@SOOEndScreen)
                newWebView.settings.javaScriptEnabled = true
                newWebView.webChromeClient = this
                newWebView.settings.javaScriptCanOpenWindowsAutomatically = true
                newWebView.settings.domStorageEnabled = true
                newWebView.settings.setSupportMultipleWindows(true)
                val transport = resultMsg.obj as WebView.WebViewTransport
                transport.webView = newWebView
                resultMsg.sendToTarget()
                return true
            }
        }
    }

    companion object {
        private const val IMAGE_MIME_TYPE = "image/*"
        private const val STRING_AGENT = "http.agent"
        const val SOO_LINK = "link"
    }
}