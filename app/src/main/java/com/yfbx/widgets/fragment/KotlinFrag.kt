package com.yfbx.widgets.fragment

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Author: Edward
 * Date: 2019/3/13
 * Description:
 */

abstract class KotlinFrag : Fragment() {


    @LayoutRes
    abstract fun getLayout(): Int

    abstract fun onCreate(view: View, savedInstanceState: Bundle?)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreate(view, savedInstanceState)
    }


}