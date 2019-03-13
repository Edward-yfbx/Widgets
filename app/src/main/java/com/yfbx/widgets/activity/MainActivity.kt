package com.yfbx.widgets.activity

import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import com.yfbx.resulthelper.ResultHelper
import com.yfbx.widgets.R
import com.yfbx.widgets.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_drawer.*
import kotlinx.android.synthetic.main.layout_toolbar.*


class MainActivity : AppCompatActivity() {

    private var oldFrag: Fragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.setNavigationOnClickListener { v -> drawer.openDrawer(Gravity.START) }
        setClick()
        switchFragment(LoadingFrag())
        requestPermission()
    }


    private fun requestPermission() {
        ResultHelper.with(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE) {

        }
    }


    private fun setClick() {
        value_txt.setOnClickListener(this::onViewClicked)
        radio_btn.setOnClickListener(this::onViewClicked)
        selector_test.setOnClickListener(this::onViewClicked)
        loading_view.setOnClickListener(this::onViewClicked)
        sound_wave.setOnClickListener(this::onViewClicked)
        roll_view.setOnClickListener(this::onViewClicked)
        draw_img.setOnClickListener(this::onViewClicked)
        chart_frag.setOnClickListener(this::onViewClicked)
    }


    private fun onViewClicked(view: View) {
        when (view.id) {
            R.id.value_txt -> switchFragment(ValueTextFrag())
            R.id.radio_btn -> switchFragment(RadioBtnFrag())
            R.id.selector_test -> switchFragment(SelectorFrag())
            R.id.loading_view -> switchFragment(LoadingFrag())
            R.id.sound_wave -> switchFragment(SoundWaveFrag())
            R.id.roll_view -> switchFragment(RollRecyclerFrag())
            R.id.draw_img -> switchFragment(DrawFragment())
            R.id.chart_frag -> switchFragment(ChartFragment())
        }
        drawer.closeDrawer(Gravity.START)
    }


    /**
     * 切换Fragment
     */
    private fun switchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        if (!fragment.isAdded) {
            transaction.add(R.id.content_view, fragment)
        }
        if (oldFrag != null) {
            transaction.hide(oldFrag)
        }
        transaction.show(fragment)
        oldFrag = fragment
        transaction.commit()
    }

}
