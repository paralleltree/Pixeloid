package net.paltee.pixeloid.ui.graph_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import net.paltee.pixeloid.AppExecutors
import net.paltee.pixeloid.R
import net.paltee.pixeloid.databinding.GraphItemBinding
import net.paltee.pixeloid.model.Graph
import net.paltee.pixeloid.ui.common.DataBoundListAdapter

class GraphListAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val clickCallbacks: GraphItemCallbacks
) : DataBoundListAdapter<Graph, GraphItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<Graph>() {
            override fun areItemsTheSame(oldItem: Graph, newItem: Graph): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Graph, newItem: Graph): Boolean {
                return oldItem.id == newItem.id &&
                        oldItem.name == newItem.name &&
                        oldItem.color == newItem.color &&
                        oldItem.type == newItem.type
            }
        }
) {
    override fun createBinding(parent: ViewGroup): GraphItemBinding {
        val binding = DataBindingUtil.inflate<GraphItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.graph_item,
                parent,
                false,
                dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.graph?.let {
                clickCallbacks?.onItemClick(it)
            }
        }
        binding.setMoreCallback {
            clickCallbacks.onMoreButtonClick(it, binding.graph!!)
        }
        return binding
    }

    override fun bind(binding: GraphItemBinding, item: Graph) {
        binding.graph = item
    }

    abstract class GraphItemCallbacks {
        abstract fun onItemClick(graph: Graph)
        abstract fun onMoreButtonClick(view: View, graph: Graph)
    }
}
