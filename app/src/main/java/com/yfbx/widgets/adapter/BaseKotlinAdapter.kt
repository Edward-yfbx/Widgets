package com.yfbx.widgets.adapter

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Author: Edward
 * Date: 2019/3/13
 * Description:
 */
abstract class BaseKotlinAdapter<T>(var data: List<T>) : RecyclerView.Adapter<BaseKotlinAdapter.Helper>() {

    protected lateinit var context: Context
    private var listener: ((position: Int, item: T) -> Unit)? = null


    @LayoutRes
    abstract fun getLayout(): Int

    abstract fun bindData(helper: Helper, position: Int, item: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Helper {
        context = parent.context
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