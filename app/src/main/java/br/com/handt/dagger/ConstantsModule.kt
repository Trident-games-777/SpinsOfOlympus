package br.com.handt.dagger

import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ConstantsModule {
    @Singleton
    @Named(BASE)
    @Provides
    fun provideBase(): String = "spinsofolympus.store/sp.php"

    @Singleton
    @Named(BASE_SAFE)
    @Provides
    fun provideBaseSafe(): String = "https://spinsofolympus.store/"

    @Singleton
    @Named(ID_ONE)
    @Provides
    fun provideIdOne(): String = "e8750730-288d-4819-9935-a268abfc35a2"

    @Singleton
    @Named(ID_FL)
    @Provides
    fun provideIdFl(): String = "JY5JV6wbMytVazio5oAFKa"

    companion object {
        const val BASE = "base"
        const val BASE_SAFE = "base_safe"
        const val ID_ONE = "one"
        const val ID_FL = "fl"
    }
}