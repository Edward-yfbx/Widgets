package com.yfbx.widgets.activity

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import com.yfbx.helper.request
import com.yfbx.widgets.R
import com.yfbx.widgets.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_drawer.*
import kotlinx.android.synthetic.main.layout_toolbar.*


class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.setNavigationOnClickListener { drawer.openDrawer(GravityCompat.START) }
        setClick()
        switchFragment(RollRecyclerFrag())

        request(Manifest.permission.WRITE_EXTERNAL_STORAGE) {
        }
    }


    private fun setClick() {
        value_txt.setOnClickListener(this::onViewClicked)
        radio_btn.setOnClickListener(this::onViewClicked)
        roll_view.setOnClickListener(this::onViewClicked)
        draw_img.setOnClickListener(this::onViewClicked)
        chart_frag.setOnClickListener(this::onViewClicked)
        anim_frag.setOnClickListener(this::onViewClicked)
    }


    private fun onViewClicked(view: View) {
        when (view.id) {
            R.id.value_txt -> switchFragment(ValueTextFrag())
            R.id.radio_btn -> switchFragment(RadioBtnFrag())
            R.id.roll_view -> switchFragment(RollRecyclerFrag())
            R.id.draw_img -> switchFragment(DrawFragment())
            R.id.chart_frag -> switchFragment(ChartFragment())
            R.id.anim_frag -> switchFragment(AnimFragment())
        }
        drawer.closeDrawer(GravityCompat.START)
    }


}
