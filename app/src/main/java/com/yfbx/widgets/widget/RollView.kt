package com.yfbx.widgets.widget

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * Author: Edward
 * Date: 2018/12/10
 * Description: Recycler 轮播
 */
class RollView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : RecyclerView(context, attrs, defStyle), Runnable {

    private var snapHelper = PagerSnapHelper()
    private var duration = 3000L//轮播间隔时间
    private var speed = 5//每 1ms 移动的距离,控制滚动速度
    private val mHandler = Handler()

    init {
        snapHelper.attachToRecyclerView(this)
    }

    /**
     * 设置轮播间隔时间
     */
    fun setDuration(duration: Long) {
        this.duration = duration
    }

    /**
     * 设置滚动速度
     */
    fun setSpeed(speed: Int) {
        this.speed = speed
    }

    /**
     * 开始轮播
     */
    fun start() {
        mHandler.post(this)
    }

    /**
     * 停止轮播
     */
    fun stop() {
        mHandler.removeCallbacks(this)
    }


    /**
     * 轮播
     */
    override fun run() {
        anim()
        mHandler.postDelayed(this, duration)
    }


    /**
     * 每 1ms 移动 5px
     */
    private fun anim() {
        if (layoutManager!!.canScrollVertically()) {
            scrollBy(0, speed)
        } else {
            scrollBy(speed, 0)
        }
        if (distance() != 0) {
            Handler().postDelayed({ anim() }, 1)
        }
    }

    /**
     * 距离目标位置距离
     */
    private fun distance(): Int {
        val snapView = snapHelper.findSnapView(layoutManager)
        snapView?.let {
            val ints = snapHelper.calculateDistanceToFinalSnap(layoutManager!!, snapView)
            ints?.let {
                return if (layoutManager!!.canScrollVertically()) ints[1] else ints[0]
            }
        }
        return 0
    }

    /**
     *  从Window 移除时,自动停止
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    /**
     * 数据适配器
     */
    fun <T> setAdapter(@LayoutRes itemLayout: Int, data: List<T>, bindData: (itemView: View, item: T) -> Unit) {
        adapter = object : Adapter<ViewHolder>() {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                return ViewHelper(LayoutInflater.from(context).inflate(itemLayout, parent, false))
            }

            override fun getItemCount(): Int {
                return if (data.isEmpty()) 0 else Integer.MAX_VALUE
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val newPosition = position % data.size
                holder.itemView.tag = newPosition
                bindData.invoke(holder.itemView, data[newPosition])
            }
        }
    }

    class ViewHelper(view: View) : ViewHolder(view)
}

