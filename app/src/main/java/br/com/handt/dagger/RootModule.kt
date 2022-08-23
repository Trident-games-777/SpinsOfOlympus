package br.com.handt.dagger

import android.content.Context
import br.com.handt.helpers.RootChecker
import dagger.Module
import dagger.Provides

@Module
class RootModule {
    @Provides
    fun provideRootChecker(context: Context): RootChecker {
        return RootChecker(context)
    }
}