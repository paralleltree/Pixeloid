package net.paltee.pixeloid.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.paltee.pixeloid.ui.graph_list.GraphListFragment
import net.paltee.pixeloid.ui.login.LoginFragment
import net.paltee.pixeloid.ui.user_list.UserListFragment

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeGraphListFragment(): GraphListFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeUserListFragment(): UserListFragment
}
