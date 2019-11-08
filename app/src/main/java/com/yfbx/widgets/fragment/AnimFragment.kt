package com.yfbx.widgets.fragment

import android.animation.AnimatorInflater
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import com.yfbx.widgets.R
import kotlinx.android.synthetic.main.frag_anim.*

/**
 * Author: Edward
 * Date: 2019/3/13
 * Description:
 */
class AnimFragment : BaseFragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setImgRotateAnim()
        setValueAnim()
    }

    override fun getLayout(): Int {
        return R.layout.frag_anim
    }

    private fun setImgRotateAnim() {
        val anim = AnimatorInflater.loadAnimator(activity, R.animator.rotate_center)
        anim.setTarget(menu_img)
        menu_img.setOnClickListener { anim.start() }
    }

    private fun setValueAnim() {
        val anim = ValueAnimator.ofFloat(0f, 100f)
        anim.duration = 10 * 1000
        anim.repeatCount = 10
        anim.repeatMode = ValueAnimator.REVERSE
        anim.addUpdateListener {
            val progress = it.animatedValue
            progress_view.setProgress(progress as Float)
        }
        anim.start()
    }

}