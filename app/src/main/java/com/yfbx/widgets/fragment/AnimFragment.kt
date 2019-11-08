package com.yfbx.widgets.fragment

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import com.yfbx.widgets.R
import com.yfbx.widgets.util.loadAnim
import kotlinx.android.synthetic.main.frag_anim.*

/**
 * Author: Edward
 * Date: 2019/3/13
 * Description:
 */
class AnimFragment : BaseFragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProgress()
        loadAnimRes()
        waveAnim()
    }

    override fun getLayout(): Int {
        return R.layout.frag_anim
    }

    /**
     * 自定义进度条
     */
    private fun setProgress() {
        val anim = ValueAnimator.ofFloat(0f, 100f)
        anim.duration = 10 * 1000
        anim.repeatCount = 10
        anim.addUpdateListener {
            val progress = it.animatedValue
            progressBar.setProgress(progress as Float)
        }
        anim.start()
    }

    /**
     * 加载动画资源
     */
    private fun loadAnimRes() {
        menuIcon.setOnClickListener { menuIcon.loadAnim(R.animator.rotate_center) }
    }

    /**
     * 绘制动画
     */
    private fun waveAnim() {
        waveView.setOnClickListener {
            if (waveView.isPlaying()) {
                waveView.stop()
            } else {
                waveView.start()
            }
        }
    }


}