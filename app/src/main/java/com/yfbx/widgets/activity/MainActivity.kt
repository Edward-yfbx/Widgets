package com.yfbx.widgets.activity

import android.os.Bundle
import com.yfbx.widgets.R
import com.yfbx.widgets.adapter.bind
import com.yfbx.widgets.bean.Menu
import com.yfbx.widgets.util.startScheme
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_menu.*


class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val menus = arrayListOf(
                Menu.ANIM,
                Menu.DRAW,
                Menu.RADIO,
                Menu.ROLL,
                Menu.TEXT,
                Menu.IMAGE,
                Menu.TEST,
                Menu.WEB
        )

        recycleView.bind(R.layout.item_menu, menus) { helper, item ->
            helper.btn.text = item.title
            helper.btn.setOnClickListener { startScheme(item.scheme) }
        }
    }

}
