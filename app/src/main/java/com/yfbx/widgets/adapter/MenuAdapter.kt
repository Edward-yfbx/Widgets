package com.yfbx.widgets.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yfbx.widgets.R
import com.yfbx.widgets.bean.Menu
import com.yfbx.widgets.util.startScheme
import kotlinx.android.synthetic.main.item_menu.view.*

/**
 * Author: Edward
 * Date: 2019-12-19
 * Description:
 */
class MenuAdapter(list: List<Menu>) : BaseQuickAdapter<Menu, BaseViewHolder>(R.layout.item_menu, list) {


    override fun convert(helper: BaseViewHolder, item: Menu) {
        val nameTxt = helper.itemView.nameTxt

        nameTxt.text = item.title
        nameTxt.setOnClickListener { mContext.startScheme(item.scheme) }
    }

}