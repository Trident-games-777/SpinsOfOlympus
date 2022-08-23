package br.com.handt

import android.app.Application
import android.content.Context
import br.com.handt.dagger.AppComponent
import br.com.handt.dagger.DaggerAppComponent

class SOOApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appContext(this).build()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is SOOApplication -> appComponent
        else -> this.applicationContext.appComponent
    }