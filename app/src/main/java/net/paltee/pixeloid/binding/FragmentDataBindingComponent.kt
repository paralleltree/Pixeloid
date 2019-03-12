package net.paltee.pixeloid.binding

import androidx.databinding.DataBindingComponent
import androidx.fragment.app.Fragment

class FragmentDataBindingComponent(fragment: Fragment) : DataBindingComponent {
    private val adapter = FragmentBindingAdapters(fragment)

    override fun getBindingAdapters(): BindingAdapters {
        return BindingAdapters
    }
}
