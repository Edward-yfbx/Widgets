package com.yfbx.widgets.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yfbx.widgets.R
import com.yfbx.widgets.util.ViewHelper
import com.yfbx.widgets.bean.Menu
import com.yfbx.widgets.util.startScheme
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_menu.*


class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycleView.adapter = adapter
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

    private val adapter = object : RecyclerView.Adapter<ViewHelper>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHelper {
            return ViewHelper(layoutInflater.inflate(R.layout.item_menu, parent, false))
        }

        override fun getItemCount(): Int {
            return menus.size
        }

        override fun onBindViewHolder(holder: ViewHelper, position: Int) {
            holder.btn.text = menus[position].title
            holder.btn.setOnClickListener { startScheme(menus[position].scheme) }
        }

    }
}
