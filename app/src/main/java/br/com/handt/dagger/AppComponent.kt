package br.com.handt.dagger

import android.content.Context
import br.com.handt.ui.screens.SOOEndScreen
import br.com.handt.ui.screens.SOOMiddleScreen
import br.com.handt.ui.screens.SOOStartScreen
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RootModule::class, PreferencesModule::class, ConstantsModule::class])
interface AppComponent {
    fun inject(activity: SOOStartScreen)
    fun inject(activity: SOOMiddleScreen)
    fun inject(activity: SOOEndScreen)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(context: Context): Builder
        fun build(): AppComponent
    }
}