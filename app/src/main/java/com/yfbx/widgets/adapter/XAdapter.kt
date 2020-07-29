package com.yfbx.widgets.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
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

    private val adapter = MultiItemAdapter()

    /**
     * 将layoutId做为itemViewType
     */
    fun <T> add(layoutId: Int, items: List<T>, binder: (helper: ViewHelper, item: T) -> Unit) {
        add(layoutId, layoutId, items, binder)
    }

    fun <T> add(layoutId: Int, item: T, binder: (helper: ViewHelper, item: T) -> Unit) {
        add(layoutId, layoutId, item, binder)
    }

    /**
     * 自定义 itemViewType
     */
    fun <T> add(viewType: Int, layoutId: Int, items: List<T>, binder: (helper: ViewHelper, item: T) -> Unit) {
        adapter.addItems(viewType, layoutId, items, binder)
    }

    fun <T> add(viewType: Int, layoutId: Int, item: T, binder: (helper: ViewHelper, item: T) -> Unit) {
        adapter.addItems(viewType, layoutId, listOf(item), binder)
    }

    fun build(): MultiItemAdapter {
        return adapter
    }
}

class MultiItemAdapter : RecyclerView.Adapter<ViewHelper>() {
    private val data = mutableListOf<ItemEntity<*>>()

    //<ViewType,layoutId>
    private val layouts = SparseArrayCompat<Int>()

    /**
     * 添加 布局&数据列表
     */
    fun <T> addItems(viewType: Int, layoutId: Int, items: List<T>, binder: (helper: ViewHelper, item: T) -> Unit) {
        if (!layouts.containsKey(viewType)) {
            layouts.append(viewType, layoutId)
        }
        data.addAll(items.map { ItemEntity(viewType, it, binder) })
    }

//    fun <T> addData(viewType: Int, item: T) {
//        val helper = viewHelpers[viewType]
//        require(helper != null) { "This type of view has not been set!" }
//        data.add(ItemEntity(viewType, item, binder))
//    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].type
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
        data[holder.adapterPosition].onBind(holder)
    }
}

data class ItemEntity<T>(
        val type: Int,
        private val item: T,
        private val binder: (helper: ViewHelper, item: T) -> Unit) {


    fun onBind(viewHelper: ViewHelper) {
        binder.invoke(viewHelper, item)
    }
}

class ViewHelper(override val containerView: View) : BaseViewHolder(containerView), LayoutContainer