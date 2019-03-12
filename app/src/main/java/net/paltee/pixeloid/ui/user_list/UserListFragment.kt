package net.paltee.pixeloid.ui.user_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import net.paltee.pixeloid.AppExecutors
import net.paltee.pixeloid.R
import net.paltee.pixeloid.binding.FragmentDataBindingComponent
import net.paltee.pixeloid.databinding.FragmentUserListBinding
import net.paltee.pixeloid.di.Injectable
import net.paltee.pixeloid.model.User
import net.paltee.pixeloid.util.autoCleared
import javax.inject.Inject

class UserListFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<FragmentUserListBinding>()

    var adapter by autoCleared<UserListAdapter>()

    lateinit var userListViewModel: UserListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false, dataBindingComponent)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userListViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(UserListViewModel::class.java)
        binding.setLifecycleOwner(viewLifecycleOwner)
        initRecyclerView()
        val rvAdapter = UserListAdapter(
                dataBindingComponent = dataBindingComponent,
                appExecutors = appExecutors,
                clickCallbacks = object : UserListAdapter.UserItemCallbacks() {
                    override fun onItemClick(user: User) {
                    }
                }
        )
        binding.userList.adapter = rvAdapter
        adapter = rvAdapter
    }

    private fun initRecyclerView() {
        binding.users = userListViewModel.users
        userListViewModel.users.observe(viewLifecycleOwner, Observer { users ->
            adapter.submitList(users)
        })
    }
}
