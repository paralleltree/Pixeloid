package net.paltee.pixeloid.ui.login

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
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import net.paltee.pixeloid.AppExecutors
import net.paltee.pixeloid.R
import net.paltee.pixeloid.binding.FragmentDataBindingComponent
import net.paltee.pixeloid.databinding.FragmentLoginBinding
import net.paltee.pixeloid.di.Injectable
import net.paltee.pixeloid.model.Status
import net.paltee.pixeloid.util.autoCleared
import javax.inject.Inject

class LoginFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    var databindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<FragmentLoginBinding>()

    lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false, databindingComponent)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loginViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(LoginViewModel::class.java)
        binding.setLifecycleOwner(viewLifecycleOwner)

        binding.setLoginCallback {
            loginViewModel.login(binding.username!!, binding.token!!)
        }

        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    loginViewModel.storeCredential()
                    navController().navigate(R.id.action_login_go_back)
                }
                Status.ERROR -> Snackbar.make(view, R.string.load_fail, Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    fun navController() = findNavController()
}
