package com.yfbx.widgets.activity

import android.os.Bundle
import com.yfbx.widgets.R
import com.yfbx.widgets.adapter.bind
import com.yfbx.widgets.bean.Menu
import com.yfbx.widgets.util.startScheme
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_menu.*
import kotlinx.android.synthetic.main.item_menu_test.*


class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycleView.bind(R.layout.item_menu, menus) { helper, item ->
            helper.btn.text = item.title
            helper.btn.setOnClickListener { startScheme(item.scheme) }
        }

        recycleView.bind {
            bind<String>(R.layout.item_menu_test, "Group-1") { helper, item ->
                helper.textView.text = item
            }
            bind<Menu>(R.layout.item_menu, menus) { helper, item ->
                helper.btn.text = item.title
                helper.btn.setOnClickListener { startScheme(item.scheme) }
            }
            bind<String>(R.layout.item_menu_test, "Group-2") { helper, item ->
                helper.textView.text = item
            }
            bind<String>(R.layout.item_menu_test, listOf("test1", "test2", "test3")) { helper, item ->
                helper.textView.text = item
            }
        }
    }

    private val menus = arrayListOf(
            Menu.ANIM,
            Menu.DRAW,
            Menu.RADIO,
            Menu.ROLL,
            Menu.TEXT,
            Menu.TEST,
            Menu.WEB
    )
}
