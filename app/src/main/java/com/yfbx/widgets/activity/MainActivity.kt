package com.yfbx.widgets.activity

import android.os.Bundle
import android.view.Gravity
import com.yfbx.widgets.R
import com.yfbx.widgets.adapter.ViewHelper
import com.yfbx.widgets.adapter.bind
import com.yfbx.widgets.bean.Menu
import com.yfbx.widgets.util.findColor
import com.yfbx.widgets.util.startScheme
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_menu.*
import kotlinx.android.synthetic.main.item_menu_test.*


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
                Menu.TEST,
                Menu.WEB
        )

        //单布局
        recycleView.bind(R.layout.item_menu, menus) { helper, item ->
            helper.btn.text = item.title
            helper.btn.setOnClickListener { startScheme(item.scheme) }
        }

        //多布局
        recycleView.bind {
            bind(R.layout.item_menu_test, "Group-1") { helper, item ->
                groupBinder(helper, item)
            }
            bind<Menu>(R.layout.item_menu, menus) { helper, item ->
                helper.btn.text = item.title
                helper.btn.setOnClickListener { startScheme(item.scheme) }
            }
            bind(R.layout.item_menu_test, "Group-2") { helper, item ->
                groupBinder(helper, item)
            }
            bind(R.layout.item_menu_test, "test1", "test2", "test3") { helper, item ->
                helper.textView.text = item
            }
            bind(R.layout.item_menu_test, "Group-3") { helper, item ->
                groupBinder(helper, item)
            }
            bind<Menu>(R.layout.item_menu, menus) { helper, item ->
                helper.btn.text = item.title
                helper.btn.setOnClickListener { startScheme(item.scheme) }
            }
        }
    }


    private fun groupBinder(helper: ViewHelper, item: String) {
        helper.textView.setBackgroundResource(R.color.background)
        helper.textView.setTextColor(findColor(R.color.fontDark))
        helper.textView.gravity = Gravity.START
        helper.textView.text = item
    }
}
