package net.paltee.pixeloid.ui.edit_graph

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
import net.paltee.pixeloid.databinding.FragmentEditGraphBinding
import net.paltee.pixeloid.di.Injectable
import net.paltee.pixeloid.model.Status
import net.paltee.pixeloid.util.autoCleared
import javax.inject.Inject

class EditGraphFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<FragmentEditGraphBinding>()

    lateinit var editGraphViewModel: EditGraphViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_graph, container, false, dataBindingComponent)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editGraphViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(EditGraphViewModel::class.java)
        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.graph = EditGraphFragmentArgs.fromBundle(arguments!!).graph
        binding.setDoneCallback {
            editGraphViewModel.send(EditGraphFragmentArgs.fromBundle(arguments!!).isNew, binding.graph!!)
        }

        editGraphViewModel.response.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> navController().navigateUp()
                Status.ERROR -> Snackbar.make(view, R.string.query_fail, Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    fun navController() = findNavController()
}
