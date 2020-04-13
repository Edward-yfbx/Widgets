package com.yfbx.widgets.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

/**
 * Author: Edward
 * Date: 2019/3/13
 * Description:
 */
abstract class BaseAdapter<T> : RecyclerView.Adapter<ViewHelper>(), LayoutContainer {

    lateinit var context: Context

    override val containerView: View? by lazy {
        FrameLayout(context)
    }
    private var onItemClick: ((item: T, index: Int) -> Unit)? = null
    var data = mutableListOf<T>()

    @LayoutRes
    abstract fun layout(): Int

    abstract fun bindData(item: T, position: Int)

    fun setNewData(list: MutableList<T>) {
        data = list
        notifyDataSetChanged()
    }

    fun addData(list: List<T>) {
        data.addAll(list)
        notifyItemRangeInserted(data.size - list.size, list.size)
        compatibilityDataSizeChanged(list.size)
    }

    fun addData(item: T) {
        data.add(item)
        notifyItemInserted(data.size)
        compatibilityDataSizeChanged(1)
    }

    fun addData(position: Int, item: T) {
        data.add(position, item)
        notifyItemInserted(position)
        compatibilityDataSizeChanged(1)
    }

    fun remove(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
        compatibilityDataSizeChanged(0)
        notifyItemRangeChanged(position, data.size - position)
    }


    fun onItemClick(onItemClick: (item: T, index: Int) -> Unit) {
        this.onItemClick = onItemClick
    }

    private fun compatibilityDataSizeChanged(size: Int) {
        if (data.size == size) {
            notifyDataSetChanged()
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHelper {
        val view = LayoutInflater.from(parent.context).inflate(layout(), parent, false)
        return ViewHelper(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(helper: ViewHelper, position: Int) {
        val index = helper.adapterPosition
        val item = data[index]
        helper.itemView.setOnClickListener { onItemClick?.invoke(item, index) }
        bindData(item, index)
    }
}

class ViewHelper(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer