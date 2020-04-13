package com.yfbx.widgets.adapter

import com.yfbx.widgets.R
import com.yfbx.widgets.bean.Menu
import com.yfbx.widgets.util.startScheme
import kotlinx.android.synthetic.main.item_menu.*

/**
 * Author: Edward
 * Date: 2020-01-14
 * Description:
 */
class TestAdapter(list: List<Menu>) : BaseAdapter<Menu>() {

    init {
        addData(list)
    }

    override fun layout(): Int {
        return R.layout.item_menu
    }

    override fun bindData(item: Menu, position: Int) {
        val context = nameTxt.context
        nameTxt.text = item.title
        nameTxt.setOnClickListener { context.startScheme(item.scheme) }
    }

}