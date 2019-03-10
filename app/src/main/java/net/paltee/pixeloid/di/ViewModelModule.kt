package net.paltee.pixeloid.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import net.paltee.pixeloid.viewmodel.PixeloidViewModelFactory

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: PixeloidViewModelFactory): ViewModelProvider.Factory
}
