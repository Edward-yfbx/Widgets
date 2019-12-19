package com.yfbx.widgets.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yfbx.widgets.bean.Menu
import com.yfbx.widgets.util.matchParent
import kotlin.math.ceil

/**
 * Author: Edward
 * Date: 2019-12-19
 * Description:
 */
class PageAdapter(data: List<Menu>, private val column: Int = 4) : RecyclerView.Adapter<PageHelper>() {

    private val pages = mutableListOf<List<Menu>>()

    init {
        val pageSize = column * 2
        val groupSize = ceil(data.size / pageSize.toFloat()).toInt()
        for (i in 0 until groupSize) {
            val start = i * pageSize
            var end = (i + 1) * pageSize
            end = if (end <= data.size) end else data.size
            pages.add(data.subList(start, end))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHelper {
        val recyclerView = RecyclerView(parent.context)
        recyclerView.layoutParams = ViewGroup.LayoutParams(matchParent(), matchParent())
        recyclerView.layoutManager = GridLayoutManager(parent.context, column)
        return PageHelper(recyclerView)
    }

    override fun getItemCount(): Int {
        return pages.size
    }

    override fun onBindViewHolder(holder: PageHelper, position: Int) {
        val recyclerView = holder.itemView as RecyclerView
        recyclerView.adapter = MenuAdapter(pages[position])
    }
}

class PageHelper(view: View) : RecyclerView.ViewHolder(view)