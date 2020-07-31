package com.yfbx.widgets.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.extensions.LayoutContainer

/**
 * Author: Edward
 * Date: 2020-07-28
 * Description:
 */
/**
 * 单布局
 */
inline fun <reified T> RecyclerView.bind(layoutId: Int, data: List<T>, crossinline binder: (helper: ViewHelper, item: T) -> Unit): MultiItemAdapter {
    val multiItemAdapter = MultiItemAdapter().apply {
        add(layoutId, data, binder)
    }
    adapter = multiItemAdapter
    return multiItemAdapter
}

/**
 * 多布局
 */
fun RecyclerView.bind(builder: MultiItemAdapter.() -> Unit): MultiItemAdapter {
    val multiItemAdapter = MultiItemAdapter().apply(builder)
    adapter = multiItemAdapter
    return multiItemAdapter
}

class MultiItemAdapter : RecyclerView.Adapter<ViewHelper>() {

    //<viewType,layoutId>
    val layouts = mutableMapOf<Int, Int>()

    //<viewType,binder>
    val binders = mutableMapOf<Int, Binder<*>>()

    val data = mutableListOf<Any>()

    inline fun <reified T> add(layoutId: Int, data: T, crossinline binder: (helper: ViewHelper, item: T) -> Unit) {
        bind(layoutId, data, object : Binder<T> {
            override fun onBind(viewHelper: ViewHelper, item: Any) {
                binder.invoke(viewHelper, item as T)
            }
        })
    }

    inline fun <reified T> add(layoutId: Int, data: List<T>, crossinline binder: (helper: ViewHelper, item: T) -> Unit) {
        bind(layoutId, data, object : Binder<T> {
            override fun onBind(viewHelper: ViewHelper, item: Any) {
                binder.invoke(viewHelper, item as T)
            }
        })
    }

    inline fun <reified T> bind(layoutId: Int, items: List<T>, binder: Binder<T>) {
        items.forEach { bind(layoutId, it, binder) }
    }

    inline fun <reified T> bind(layoutId: Int, item: T, binder: Binder<T>) {
        bind(layoutId, binder)
        data.add(item as Any)
    }

    inline fun <reified T> bind(layoutId: Int, binder: Binder<T>) {
        val viewType = T::class.java.name.hashCode()
        if (!layouts.containsKey(viewType)) {
            layouts[viewType] = layoutId
        }
        if (!binders.containsKey(viewType)) {
            binders[viewType] = binder
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    /**
     * 用 class name 作为 view type
     */
    override fun getItemViewType(position: Int): Int {
        val clazz = data[position]::class.java
        return clazz.name.hashCode()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHelper {
        val layoutId = layouts[viewType]
        require(layoutId != null) { "This type #$viewType of view  was not found!" }
        return ViewHelper(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHelper, position: Int) {
        onBindViewHolder(holder, position, mutableListOf())
    }

    override fun onBindViewHolder(holder: ViewHelper, position: Int, payloads: MutableList<Any>) {
        val index = holder.adapterPosition
        val item = data[index]
        val viewType = getItemViewType(index)
        val binder = binders[viewType]
        binder?.onBind(holder, item)
    }


    fun addData(item: Any, position: Int = itemCount) {
        require(position in 0..itemCount) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        data.add(position, item)
        notifyItemInserted(position)
        compatibilityDataSizeChanged(1)
    }

    fun addData(items: List<Any>, position: Int = itemCount) {
        require(position in 0..itemCount) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        data.addAll(position, items)
        notifyItemRangeInserted(position, items.size)
        compatibilityDataSizeChanged(items.size)
    }

    fun remove(position: Int) {
        require(position in 0 until data.size) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        data.removeAt(position)
        notifyItemRemoved(position)
        compatibilityDataSizeChanged(0)
        notifyItemRangeChanged(position, data.size - position)
    }

    fun update(position: Int, item: Any) {
        require(position in 0 until data.size) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        data[position] = item
        notifyItemChanged(position)
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(position: Int): T {
        require(position in 0 until data.size) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        return data[position] as T
    }


    private fun compatibilityDataSizeChanged(size: Int) {
        val dataSize = data.size
        if (dataSize == size) {
            notifyDataSetChanged()
        }
    }

}

interface Binder<T> {

    fun onBind(viewHelper: ViewHelper, item: Any)
}

class ViewHelper(override val containerView: View) : BaseViewHolder(containerView), LayoutContainer