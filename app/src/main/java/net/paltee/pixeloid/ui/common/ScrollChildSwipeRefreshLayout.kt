package net.paltee.pixeloid.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class ScrollChildSwipeRefreshLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : SwipeRefreshLayout(context, attrs) {
    private var mScrollUpChild: View? = null

    override fun canChildScrollUp(): Boolean {
        return if (mScrollUpChild != null) {
            mScrollUpChild!!.canScrollVertically(-1)
        } else super.canChildScrollUp()
    }

    fun setScrollUpChild(view: View) {
        mScrollUpChild = view
    }
}
