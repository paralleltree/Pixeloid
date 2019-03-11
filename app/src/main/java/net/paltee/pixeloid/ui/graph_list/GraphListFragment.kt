package net.paltee.pixeloid.ui.graph_list

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
import net.paltee.pixeloid.databinding.FragmentGraphListBinding
import net.paltee.pixeloid.di.Injectable
import net.paltee.pixeloid.model.Status
import net.paltee.pixeloid.util.autoCleared
import javax.inject.Inject

class GraphListFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<FragmentGraphListBinding>()

    var adapter by autoCleared<GraphListAdapter>()

    lateinit var graphListViewModel: GraphListViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_graph_list, container, false, dataBindingComponent)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        graphListViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(GraphListViewModel::class.java)
        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.refreshContainer.setOnRefreshListener { graphListViewModel.retry() }
        initRecyclerView()
        val rvAdapter = GraphListAdapter(
                dataBindingComponent = dataBindingComponent,
                appExecutors = appExecutors
        ) { graph ->
            // onClick
            graphListViewModel.incrementGraph(graph)
        }
        binding.graphList.adapter = rvAdapter
        adapter = rvAdapter

        graphListViewModel.pixelResponse.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> Snackbar.make(view, R.string.query_successful, Snackbar.LENGTH_SHORT).show()
                Status.ERROR -> Snackbar.make(view, R.string.query_fail, Snackbar.LENGTH_SHORT).show()
            }
        })

        graphListViewModel.user.observe(viewLifecycleOwner, Observer { user ->
            if (user == null) {
                navController().navigate(R.id.action_main_go_login)
            }
        })
    }

    private fun initRecyclerView() {
        binding.graphs = graphListViewModel.graphs
        graphListViewModel.graphs.observe(viewLifecycleOwner, Observer { result ->
            binding.isLoading = when (result.status) {
                Status.LOADING -> true
                Status.SUCCESS -> false
                Status.ERROR -> false
            }
            if (result.status == Status.ERROR) {
                Snackbar.make(binding.graphList, R.string.load_fail, Snackbar.LENGTH_SHORT).show()
            }
            adapter.submitList(result?.data)
        })
    }

    fun navController() = findNavController()
}
