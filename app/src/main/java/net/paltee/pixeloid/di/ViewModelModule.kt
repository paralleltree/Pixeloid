package net.paltee.pixeloid.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import net.paltee.pixeloid.ui.edit_graph.EditGraphViewModel
import net.paltee.pixeloid.ui.graph_list.GraphListViewModel
import net.paltee.pixeloid.ui.login.LoginViewModel
import net.paltee.pixeloid.ui.user_list.UserListViewModel
import net.paltee.pixeloid.viewmodel.PixeloidViewModelFactory

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(EditGraphViewModel::class)
    abstract fun bindEditGraphViewModel(editGraphViewModel: EditGraphViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GraphListViewModel::class)
    abstract fun bindGraphListViewModel(graphListViewModel: GraphListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModel::class)
    abstract fun bindUserListViewModel(userListViewModel: UserListViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: PixeloidViewModelFactory): ViewModelProvider.Factory
}
