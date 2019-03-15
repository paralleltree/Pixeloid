package net.paltee.pixeloid.ui.graph_list

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
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
import net.paltee.pixeloid.model.Graph
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
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_graph_list, container, false, dataBindingComponent)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.root_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.users_list -> navController().navigate(R.id.show_users)
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        graphListViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(GraphListViewModel::class.java)
        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.refreshContainer.setOnRefreshListener { graphListViewModel.retry() }
        initRecyclerView()
        val rvAdapter = GraphListAdapter(
                dataBindingComponent = dataBindingComponent,
                appExecutors = appExecutors,
                clickCallbacks = object : GraphListAdapter.GraphItemCallbacks() {
                    override fun onItemClick(graph: Graph) = graphListViewModel.incrementGraph(graph)

                    override fun onMoreButtonClick(view: View, graph: Graph) {
                        val context = this@GraphListFragment.context!!
                        val popup = PopupMenu(context, view)
                        popup.menuInflater.inflate(R.menu.graph_item_menu, popup.menu)
                        popup.setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.open_graph -> {
                                    val url = Uri.parse("https://pixe.la/v1/users/${graphListViewModel.user.value?.name}/graphs/${graph.id}.html")
                                    val intent = Intent(Intent.ACTION_VIEW, url)
                                    context.startActivity(intent)
                                }
                            }
                            true
                        }
                        popup.show()
                    }
                }
        )
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
                navController().navigate(R.id.go_login)
            }
        })
    }

    private fun initRecyclerView() {
        binding.graphs = graphListViewModel.graphs
        graphListViewModel.graphs.observe(viewLifecycleOwner, Observer { result ->
            binding.isLoading = when (result?.status) {
                Status.LOADING -> true
                Status.SUCCESS -> false
                Status.ERROR -> false
                else -> false
            }
            if (result?.status == Status.ERROR) {
                Snackbar.make(binding.graphList, R.string.load_fail, Snackbar.LENGTH_SHORT).show()
            }
            adapter.submitList(result?.data)
        })
    }

    fun navController() = findNavController()
}
