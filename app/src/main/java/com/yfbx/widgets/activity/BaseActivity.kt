package com.yfbx.widgets.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * Author: Edward
 * Date: 2019-11-08
 * Description:
 */
abstract class BaseActivity : AppCompatActivity() {

    private var oldFrag: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState, persistentState)
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


    protected fun getContentView(): View {
        val container = findViewById<FrameLayout>(android.R.id.content)
        return container.getChildAt(0)
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

}