package net.paltee.pixeloid.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.paltee.pixeloid.ui.graph_list.GraphListFragment

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeGraphListFragment(): GraphListFragment
}
