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
fun RecyclerView.bind(builder: AdapterBuilder.() -> Unit): MultiItemAdapter {
    val xAdapter = AdapterBuilder().apply(builder).build()
    adapter = xAdapter
    return xAdapter
}

class AdapterBuilder {

    val adapter = MultiItemAdapter()


    inline fun <reified T> add(layoutId: Int, data: T, crossinline binder: (helper: ViewHelper, item: T) -> Unit) {
        adapter.bind(layoutId, data, object : Binder<T> {
            override fun onBind(viewHelper: ViewHelper, item: Any) {
                binder.invoke(viewHelper, item as T)
            }
        })
    }

    inline fun <reified T> add(layoutId: Int, data: List<T>, crossinline binder: (helper: ViewHelper, item: T) -> Unit) {
        adapter.bind(layoutId, data, object : Binder<T> {
            override fun onBind(viewHelper: ViewHelper, item: Any) {
                binder.invoke(viewHelper, item as T)
            }
        })
    }

    fun build(): MultiItemAdapter {
        return adapter
    }
}

class MultiItemAdapter : RecyclerView.Adapter<ViewHelper>() {

    //<viewType,layoutId>
    val layouts = mutableMapOf<Int, Int>()

    //<viewType,binder>
    val binders = mutableMapOf<Int, Binder<*>>()

    val data = mutableListOf<Any>()


    inline fun <reified T> bind(layoutId: Int, binder: Binder<T>) {
        val viewType = T::class.java.name.hashCode()
        layouts[viewType] = layoutId
        binders[viewType] = binder
    }

    inline fun <reified T> bind(layoutId: Int, item: T, binder: Binder<T>) {
        val viewType = T::class.java.name.hashCode()
        layouts[viewType] = layoutId
        binders[viewType] = binder
        data.add(item as Any)
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> bind(layoutId: Int, items: List<T>, binder: Binder<T>) {
        val viewType = T::class.java.name.hashCode()
        layouts[viewType] = layoutId
        binders[viewType] = binder
        data.addAll(items as List<Any>)
    }


    fun addData(items: List<Any>, position: Int = itemCount) {
        data.addAll(position, items)
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
}

interface Binder<T> {

    fun onBind(viewHelper: ViewHelper, item: Any)
}

class ViewHelper(override val containerView: View) : BaseViewHolder(containerView), LayoutContainer