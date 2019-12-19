package com.yfbx.widgets.activity

import android.os.Bundle
import com.yfbx.widgets.R
import com.yfbx.widgets.adapter.PageAdapter
import com.yfbx.widgets.bean.Menu
import com.yfbx.widgets.util.onPageChange
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val menus = arrayListOf(
                Menu.ANIM,
                Menu.CHART,
                Menu.DRAW,
                Menu.RADIO,
                Menu.ROLL,
                Menu.TEXT,
                Menu.WEB,
                Menu.TEST,
                Menu.TEST,
                Menu.TEST,
                Menu.TEST,
                Menu.TEST
        )

        val adapter = PageAdapter(menus)
        viewPager.adapter = adapter
        indicator.setCount(adapter.itemCount)
        viewPager.onPageChange { indicator.select(it) }
    }
}
