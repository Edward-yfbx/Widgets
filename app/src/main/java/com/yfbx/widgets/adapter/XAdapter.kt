package com.yuxiaor.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.extensions.LayoutContainer

/**
 * Author: Edward
 * Date: 2020-07-28
 * Description:
 */
/**
 * 单布局
 */
inline fun <reified T> RecyclerView.bind(layoutId: Int, data: List<T>, noinline binder: (helper: ViewHelper, item: T) -> Unit): XAdapter {
    val xAdapter = adapter { bind(layoutId, data, binder) }
    adapter = xAdapter
    return xAdapter
}

/**
 * 多布局
 */
fun RecyclerView.bind(builder: XAdapter.() -> Unit): XAdapter {
    val xAdapter = adapter(builder)
    adapter = xAdapter
    return xAdapter
}

fun adapter(builder: XAdapter.() -> Unit): XAdapter {
    return XAdapter().apply(builder)
}


inline fun <reified T> XAdapter.bind(layoutId: Int, item: T, noinline binder: (helper: ViewHelper, item: T) -> Unit) {
    bind(layoutId, binder)
    add(item as Any)
}

inline fun <reified T> XAdapter.bind(view: View, item: T, noinline binder: (helper: ViewHelper, item: T) -> Unit) {
    bind(view, binder)
    add(item as Any)
}

inline fun <reified T> XAdapter.bind(layoutId: Int, items: List<T>, noinline binder: (helper: ViewHelper, item: T) -> Unit) {
    bind(layoutId, binder)
    @Suppress("UNCHECKED_CAST")
    addAll(items as List<Any>)
}

inline fun <reified T> XAdapter.bind(view: View, items: List<T>, noinline binder: (helper: ViewHelper, item: T) -> Unit) {
    bind(view, binder)
    @Suppress("UNCHECKED_CAST")
    addAll(items as List<Any>)
}

/**
 * 用 class name的 hashcode 作为 viewType
 */
inline fun <reified T> XAdapter.bind(layoutId: Int, noinline binder: (helper: ViewHelper, item: T) -> Unit) {
    val type = T::class.java.name.hashCode()
    bind(type, layoutId, binder)
}

inline fun <reified T> XAdapter.bind(view: View, noinline binder: (helper: ViewHelper, item: T) -> Unit) {
    val type = T::class.java.name.hashCode()
    bind(type, view, binder)
}

/**
 * 自己定义 viewType
 */
inline fun <reified T> XAdapter.bind(viewType: Int, view: View, noinline binder: (helper: ViewHelper, item: T) -> Unit) {
    val className = T::class.java.name
    addBinder(viewType, className, object : Binder<T>(binder) {
        override fun createXHelper(parent: ViewGroup): ViewHelper {
            return object : ViewHelper(view) {
                override fun onBind(item: Any) {
                    binder.invoke(this, item as T)
                }
            }
        }
    })
}

inline fun <reified T> XAdapter.bind(viewType: Int, layoutId: Int, noinline binder: (helper: ViewHelper, item: T) -> Unit) {
    val className = T::class.java.name
    addBinder(viewType, className, object : Binder<T>(binder) {
        override fun createXHelper(parent: ViewGroup): ViewHelper {
            val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
            return object : ViewHelper(view) {
                override fun onBind(item: Any) {
                    binder.invoke(this, item as T)
                }
            }
        }
    })
}

class XAdapter : RecyclerView.Adapter<ViewHelper>() {

    //<viewType,binder>
    private val binders = SparseArrayCompat<Binder<*>>()

    //<className,viewType>
    private val types = hashMapOf<String, Int>()

    private val data = mutableListOf<Any>()

    fun addBinder(viewType: Int, className: String, binder: Binder<*>) {
        types[className] = viewType
        binders.append(viewType, binder)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = data[position]
        val className = item::class.java.name
        val type = types[className]
        require(type != null) { "This type #$className of view  was not found!" }
        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHelper {
        val binder = binders[viewType]
        require(binder != null) { "This type #$viewType of view  was not found!" }
        return binder.createXHelper(parent)
    }

    override fun onBindViewHolder(holder: ViewHelper, position: Int) {
        onBindViewHolder(holder, position, mutableListOf())
    }

    override fun onBindViewHolder(holder: ViewHelper, position: Int, payloads: MutableList<Any>) {
        holder.onBind(data[holder.adapterPosition])
    }


    fun add(item: Any, position: Int = itemCount) {
        require(position in 0..itemCount) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        require(item !is List<*>) {
            "IllegalArgumentException:Method #add(item) is only used to add single item.try: #addAll(items)"
        }
        data.add(position, item)
        notifyInserted(position)
    }

    fun addAll(items: List<Any>, position: Int = itemCount) {
        require(position in 0..itemCount) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        data.addAll(position, items)
        notifyRangeInserted(items.size, position)
    }

    fun remove(position: Int) {
        require(position in 0 until itemCount) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        data.removeAt(position)
        notifyRemoved(position)
    }

    fun update(position: Int, item: Any) {
        require(position in 0 until itemCount) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        data[position] = item
        notifyItemChanged(position)
    }

    fun getData(): MutableList<Any> {
        return data
    }

    inline operator fun <reified T> get(position: Int): T? {
        require(position in 0 until itemCount) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        val item = getData()[position]
        return if (item is T) item else null
    }

    inline fun <reified T> getList(): List<T> {
        val list = mutableListOf<T>()
        getData().forEach {
            if (it is T) {
                list.add(it)
            }
        }
        return list
    }


    fun notifyRemoved(position: Int) {
        notifyItemRemoved(position)
        compatibilityDataSizeChanged(0)
        notifyItemRangeChanged(position, data.size - position)
    }

    fun notifyRangeInserted(size: Int, position: Int) {
        notifyItemRangeInserted(position, size)
        compatibilityDataSizeChanged(size)
    }

    fun notifyInserted(position: Int) {
        notifyItemInserted(position)
        compatibilityDataSizeChanged(1)
    }


    private fun compatibilityDataSizeChanged(size: Int) {
        val dataSize = data.size
        if (dataSize == size) {
            notifyDataSetChanged()
        }
    }
}

abstract class Binder<T>(val binder: (helper: ViewHelper, item: T) -> Unit) {

    abstract fun createXHelper(parent: ViewGroup): ViewHelper
}

abstract class ViewHelper(override val containerView: View) : BaseViewHolder(containerView), LayoutContainer {

    abstract fun onBind(item: Any)
}