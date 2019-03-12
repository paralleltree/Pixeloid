package net.paltee.pixeloid.ui.user_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import net.paltee.pixeloid.AppExecutors
import net.paltee.pixeloid.R
import net.paltee.pixeloid.databinding.UserItemBinding
import net.paltee.pixeloid.model.User
import net.paltee.pixeloid.ui.common.DataBoundListAdapter

class UserListAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val clickCallbacks: UserItemCallbacks
) : DataBoundListAdapter<User, UserItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.name == newItem.name && oldItem.token == newItem.token
            }
        }
) {
    override fun createBinding(parent: ViewGroup): UserItemBinding {
        val binding = DataBindingUtil.inflate<UserItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.user_item,
                parent,
                false,
                dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.user?.let { clickCallbacks?.onItemClick(it) }
        }
        return binding
    }

    override fun bind(binding: UserItemBinding, item: User) {
        binding.user = item
    }

    abstract class UserItemCallbacks {
        abstract fun onItemClick(user: User)
    }
}