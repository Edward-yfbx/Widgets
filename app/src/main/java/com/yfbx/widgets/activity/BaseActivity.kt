package com.yfbx.widgets.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.billy.android.swipe.SwipeConsumer
import com.yfbx.widgets.util.slideBack
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * Author: Edward
 * Date: 2019-11-08
 * Description:
 */
abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private var oldFrag: Fragment? = null
    private var slideBack: SwipeConsumer? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState, persistentState)
        slideBack = slideBack()
    }


    fun enableSlideBack(enable: Boolean) {
        if (enable) {
            slideBack?.enableLeft()
        } else {
            slideBack?.disableLeft()
        }
    }

    /**
     * Toolbar
     */
    protected fun setToolbar(title: String, back: Boolean = true) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(back)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        toolbarTxt.text = title
    }

    protected fun contentView(): FrameLayout {
        return findViewById(android.R.id.content)
    }

    /**
     * 切换Fragment
     */
    protected fun switchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        if (!fragment.isAdded) {
            //transaction.add(R.id.content_view, fragment)
        }
        if (oldFrag != null) {
            transaction.hide(oldFrag!!)
        }
        transaction.show(fragment)
        oldFrag = fragment
        transaction.commit()
    }

    override fun onDestroy() {
        cancel()
        super.onDestroy()
    }
}