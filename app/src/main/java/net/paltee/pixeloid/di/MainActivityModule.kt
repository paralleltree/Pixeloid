package net.paltee.pixeloid.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.paltee.pixeloid.MainActivity

@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}