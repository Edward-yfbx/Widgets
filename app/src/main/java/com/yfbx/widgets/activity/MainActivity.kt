package com.yfbx.widgets.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.result.ActivityResult
import com.yfbx.widgets.R
import com.yfbx.widgets.adapter.PageAdapter
import com.yfbx.widgets.bean.Menu
import com.yfbx.widgets.dialog.Loading
import com.yfbx.widgets.util.onPageChange
import com.yfbx.widgets.util.permissionFor
import com.yfbx.widgets.util.startForResult
import com.yfbx.widgets.util.takePhoto
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


        loadingBtn.setOnClickListener {
            Loading().show()
        }
    }


    fun testActivityResult() {
        //
        startForResult(Intent()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                //TODO
            }
        }


        permissionFor(Manifest.permission.WRITE_EXTERNAL_STORAGE) { grant: Boolean ->
            if (grant) {
                //TODO
            }

        }

        takePhoto { drawable: Drawable? ->
            //TODO:
        }

    }
}
