package com.yfbx.widgets.adapter

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import kotlinx.android.extensions.LayoutContainer

/**
 * Author: Edward
 * Date: 2020-07-27
 * Description:
 */


fun <T> RecyclerView.bind(@LayoutRes layoutId: Int, data: List<T>, binder: (helper: ViewHelper, item: T) -> Unit): BaseMultiItemQuickAdapter<MultiEntity<*>, ViewHelper> {
    val mAdapter = MultiCreator().apply {
        bind(layoutId, data, binder)
    }.create()
    adapter = mAdapter
    return mAdapter
}

fun RecyclerView.bind(creator: MultiCreator.() -> Unit): BaseMultiItemQuickAdapter<MultiEntity<*>, ViewHelper> {
    val mAdapter = MultiCreator().apply(creator).create()
    adapter = mAdapter
    return mAdapter
}


@Suppress("UNCHECKED_CAST")
class MultiCreator {
    private val items = mutableListOf<MultiEntity<*>>()
    private val layouts = hashMapOf<Int, Int>()

    fun <T> bind(@LayoutRes layoutId: Int, vararg data: T, binder: (helper: ViewHelper, item: T) -> Unit) {
        items.addAll(data.map { MultiEntity(it, layoutId, binder) })
        layouts[layoutId] = layoutId
    }

    fun <T> bind(@LayoutRes layoutId: Int, data: List<T>, binder: (helper: ViewHelper, item: T) -> Unit) {
        items.addAll(data.map { MultiEntity(it, layoutId, binder) })
        layouts[layoutId] = layoutId
    }

    fun create() = object : BaseMultiItemQuickAdapter<MultiEntity<*>, ViewHelper>(items) {
        init {
            for (entry in layouts) {
                addItemType(entry.key, entry.value)
            }
        }

        override fun convert(helper: ViewHelper, item: MultiEntity<*>?) {
            item?.onBind(helper)
        }
    }
}


class MultiEntity<T>(
        private val entity: T,
        private val layoutId: Int,
        private val binder: (helper: ViewHelper, item: T) -> Unit
) : MultiItemEntity {

    override fun getItemType(): Int {
        return layoutId
    }

    fun onBind(helper: ViewHelper) {
        binder.invoke(helper, entity)
    }
}


class ViewHelper(override val containerView: View) : BaseViewHolder(containerView), LayoutContainer