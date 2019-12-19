package com.yfbx.widgets.adapter

import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import com.yfbx.widgets.bean.Menu
import com.yfbx.widgets.util.dp
import com.yfbx.widgets.util.matchParent

/**
 * Author: Edward
 * Date: 2019-12-19
 * Description:
 */
class MenuAdapter(private val data: List<Menu>) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val textView = TextView(parent.context)
        return BaseViewHolder(textView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val textView = holder.itemView as TextView
        textView.layoutParams = ViewGroup.LayoutParams(matchParent(), dp(80))
        textView.gravity = Gravity.CENTER
        textView.text = data[position].title
    }

}