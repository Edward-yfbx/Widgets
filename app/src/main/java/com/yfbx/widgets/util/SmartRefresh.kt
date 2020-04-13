package com.yfbx.widgets.util

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.billy.android.swipe.SmartSwipeRefresh
import com.yfbx.widgets.R

/**
 * Author: Edward
 * Date: 2019-12-27
 * Description:
 */
/**
 * 上拉加载，下拉刷新
 */
fun View.pull(onRefresh: () -> Unit, loadMore: () -> Unit): SmartSwipeRefresh {
    return SmartSwipeRefresh.translateMode(this, false)
            .setDataLoader(object : SmartSwipeRefresh.SmartSwipeRefreshDataLoader {
                override fun onRefresh(ssr: SmartSwipeRefresh) {
                    onRefresh.invoke()
                }

                override fun onLoadMore(ssr: SmartSwipeRefresh) {
                    loadMore.invoke()
                }
            })
            .setHeader(SmartHeader(context))
            .setFooter(SmartFooter(context))
}

/**
 * 下拉刷新
 */
fun View.pull(onRefresh: () -> Unit): SmartSwipeRefresh {
    return SmartSwipeRefresh.translateMode(this, false)
            .setDataLoader(object : SmartSwipeRefresh.SmartSwipeRefreshDataLoader {
                override fun onRefresh(ssr: SmartSwipeRefresh) {
                    onRefresh.invoke()
                }

                override fun onLoadMore(ssr: SmartSwipeRefresh) {
                }
            })
            .setHeader(SmartHeader(context))
            .setFooter(SmartFooter(context))
            .disableLoadMore()
}

class RefreshView @kotlin.jvm.JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private val imageView = ImageView(context)
    private val textView = TextView(context)
    private var anim: ObjectAnimator? = null

    init {
        layoutParams = LayoutParams(matchParent(), dp(64))
        imageView.layoutParams = LayoutParams(dp(20), dp(20))
        textView.layoutParams = LayoutParams(dp(100), wrapContent())

        gravity = Gravity.CENTER

        imageView.setImageResource(R.drawable.ic_loading)

        textView.text = "下拉刷新"
        textView.textSize = 12f
        textView.setPadding(dp(10))
        textView.setColor(R.color.fontLight)

        addView(imageView)
        addView(textView)
    }

    fun setText(text: CharSequence) {
        textView.text = text
    }

    fun startAnim() {
        anim = imageView.rotate()
    }

    fun stopAnim() {
        anim?.cancel()
    }
}


class SmartHeader(context: Context) : SmartSwipeRefresh.SmartSwipeRefreshHeader {

    private val refreshView = RefreshView(context)

    override fun onInit(horizontal: Boolean) {
    }

    override fun getView(): View {
        return refreshView
    }

    override fun onStartDragging() {
    }

    override fun onProgress(dragging: Boolean, progress: Float) {
        if (dragging) {
            refreshView.setText(if (progress >= 1) "松开刷新" else "下拉刷新")
        }
    }

    override fun onDataLoading() {
        refreshView.setText("正在刷新...")
        refreshView.startAnim()
    }

    override fun onFinish(success: Boolean): Long {
        return 500
    }

    override fun onReset() {
        refreshView.setText("下拉刷新")
        refreshView.stopAnim()
    }

}


class SmartFooter(context: Context) : SmartSwipeRefresh.SmartSwipeRefreshFooter {

    private val refreshView = RefreshView(context)


    override fun onInit(horizontal: Boolean) {
    }

    override fun getView(): View {
        return refreshView
    }

    override fun onStartDragging() {
    }

    override fun onProgress(dragging: Boolean, progress: Float) {
    }

    override fun onDataLoading() {
        refreshView.setText("正在加载...")
        refreshView.startAnim()
    }

    override fun onFinish(success: Boolean): Long {
        return 500
    }

    override fun onReset() {
        refreshView.setText("加载更多")
        refreshView.stopAnim()
    }

    override fun setNoMoreData(noMoreData: Boolean) {

    }

}