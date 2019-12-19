package com.yfbx.widgets.activity

import android.animation.ValueAnimator
import android.os.Bundle
import com.yfbx.widgets.R
import com.yfbx.widgets.util.loadAnim
import kotlinx.android.synthetic.main.activity_anim.*

/**
 * Author: Edward
 * Date: 2019-12-19
 * Description:
 */
class AnimActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anim)
        setProgress()
        loadAnimRes()
        waveAnim()
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