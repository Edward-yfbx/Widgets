package com.yfbx.widgets.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Author: Edward
 * Date: 2019/3/13
 * Description:
 */
abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseAdapter.Helper>() {

    protected lateinit var context: Context
    private var listener: ((position: Int, item: T) -> Unit)? = null
    protected var data = mutableListOf<T>()

    @LayoutRes
    abstract fun getLayout(): Int

    abstract fun bindData(helper: Helper, position: Int, item: T)


    fun setNewData(data: List<T>?) {
        this.data.clear()
        if (data != null) {
            this.data.addAll(data)
        }
        notifyDataSetChanged()
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Helper {
        val view = LayoutInflater.from(context).inflate(getLayout(), parent, false)
        view.setOnClickListener(this::onClick)
        return Helper(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(helper: Helper, position: Int) {
        helper.itemView.tag = position
        bindData(helper, position, data[position])
    }


    private fun onClick(view: View) {
        if (listener != null) {
            val position = view.tag as Int
            listener!!.invoke(position, data[position])
        }
    }


    fun setOnItemClickListener(listener: ((position: Int, item: T) -> Unit)? = null) {
        this.listener = listener
    }

    class Helper(itemView: View) : RecyclerView.ViewHolder(itemView)
}