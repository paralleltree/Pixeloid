package net.paltee.pixeloid.binding

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod
import net.paltee.pixeloid.R
import java.lang.IllegalArgumentException

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @BindingAdapter("android:text")
    fun setTextViewText(textView: TextView, text: String?) {
        textView.text = text ?: ""
    }

    @JvmStatic
    @InverseMethod("toGraphType")
    fun toGraphTypeId(type: String): Int {
        when (type) {
            "int" -> return R.id.type_int
            "float" -> return R.id.type_float
            else -> throw IllegalArgumentException("Invalid color string")
        }
    }

    @JvmStatic
    fun toGraphType(id: Int): String {
        when (id) {
            R.id.type_int -> return "int"
            R.id.type_float -> return "float"
            else -> throw IllegalArgumentException("Invalid RadioGroup")
        }
    }

    @JvmStatic
    @InverseMethod("toGraphColor")
    fun toGraphColorIndex(color: String): Int {
        return graphColors.indexOf(color)
    }

    @JvmStatic
    fun toGraphColor(index: Int): String {
        return graphColors[index]
    }

    private val graphColors = listOf("shibafu", "momiji", "sora", "ichou", "ajisai", "kuro")
}
