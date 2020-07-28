package com.yfbx.widgets.adapter

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import kotlinx.android.extensions.LayoutContainer

/**
 * Author: Edward
 * Date: 2020-07-27
 * Description:
 */

fun <T> RecyclerView.bind(creator: SimpleCreator<T>.() -> Unit): BaseQuickAdapter<T, ViewHelper> {
    val mAdapter = SimpleCreator<T>().apply(creator).create()
    adapter = mAdapter
    return mAdapter
}

fun <T> RecyclerView.bind(@LayoutRes layoutId: Int, data: List<T> = listOf(), onBind: (helper: ViewHelper, item: T) -> Unit): BaseQuickAdapter<T, ViewHelper> {
    val mAdapter = SimpleCreator<T>().apply {
        setDate(data)
        bind(layoutId, onBind)
    }.create()
    adapter = mAdapter
    return mAdapter
}

fun <T> RecyclerView.bind(view: View, data: List<T> = listOf(), onBind: (helper: ViewHelper, item: T) -> Unit): BaseQuickAdapter<T, ViewHelper> {
    val mAdapter = SimpleCreator<T>().apply {
        setDate(data)
        bind(view, onBind)
    }.create()
    adapter = mAdapter
    return mAdapter
}


fun RecyclerView.bind(creator: MultiCreator.() -> Unit): BaseMultiItemQuickAdapter<MultiEntity<*>, ViewHelper> {
    val mAdapter = MultiCreator().apply(creator).create()
    adapter = mAdapter
    return mAdapter
}

class SimpleCreator<T> {
    @LayoutRes
    private var layoutId: Int = 0
    private var items: List<T> = listOf()
    private var binder: ((helper: ViewHelper, item: T) -> Unit)? = null

    fun setDate(data: List<T>) {
        this.items = data
    }

    fun bind(@LayoutRes layoutId: Int = 0, binder: (helper: ViewHelper, item: T) -> Unit) {
        this.binder = binder
        this.layoutId = layoutId
    }

    fun bind(view: View, binder: (helper: ViewHelper, item: T) -> Unit) {
        view.id = View.generateViewId()
        this.binder = binder
        this.layoutId = view.id
    }

    fun create() = object : BaseQuickAdapter<T, ViewHelper>(layoutId, items) {
        override fun convert(helper: ViewHelper, item: T) {
            binder?.invoke(helper, item)
        }
    }
}


@Suppress("UNCHECKED_CAST")
class MultiCreator {
    private val items = mutableListOf<MultiEntity<*>>()
    private val layouts = hashMapOf<Int, Int>()
    private val binders = hashMapOf<Int, (helper: ViewHelper, item: Any) -> Unit>()

    fun <T> bind(@LayoutRes layoutId: Int, data: Any, binder: (helper: ViewHelper, item: T) -> Unit) {
        items.add(MultiEntity(data, layoutId))
        layouts[layoutId] = layoutId
        binders[layoutId] = binder as ((helper: ViewHelper, item: Any) -> Unit)
    }

    fun <T> bind(@LayoutRes layoutId: Int, data: List<Any>, binder: (helper: ViewHelper, item: T) -> Unit) {
        items.addAll(data.map { MultiEntity(it, layoutId) })
        layouts[layoutId] = layoutId
        binders[layoutId] = binder as ((helper: ViewHelper, item: Any) -> Unit)
    }

    fun <T> bind(view: View, data: Any, binder: (helper: ViewHelper, item: T) -> Unit) {
        view.id = View.generateViewId()
        items.add(MultiEntity(data, view.id))
        layouts[view.id] = view.id
        binders[view.id] = binder as ((helper: ViewHelper, item: Any) -> Unit)
    }

    fun <T> bind(view: View, data: List<Any>, binder: (helper: ViewHelper, item: T) -> Unit) {
        view.id = View.generateViewId()
        items.addAll(data.map { MultiEntity(it, view.id) })
        layouts[view.id] = view.id
        binders[view.id] = binder as ((helper: ViewHelper, item: Any) -> Unit)
    }


    fun create() = object : BaseMultiItemQuickAdapter<MultiEntity<*>, ViewHelper>(items) {
        init {
            for (entry in layouts) {
                addItemType(entry.key, entry.value)
            }
        }

        override fun convert(helper: ViewHelper, item: MultiEntity<*>?) {
            item?.let {
                binders[it.itemType]?.invoke(helper, item.item!!)
            }
        }
    }
}


class MultiEntity<T>(val item: T, private val type: Int) : MultiItemEntity {

    override fun getItemType(): Int {
        return type
    }
}


class ViewHelper(override val containerView: View) : BaseViewHolder(containerView), LayoutContainer