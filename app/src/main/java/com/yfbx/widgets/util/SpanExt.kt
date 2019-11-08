package com.yfbx.widgets.util

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.AbsoluteSizeSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View

/**
 * Author: Edward
 * Date: 2019-09-04
 * Description:
 */
fun SpannableString.setColor(start: Int, end: Int, color: Int): SpannableString {
    setSpan(ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

fun SpannableString.setSize(start: Int, end: Int, size: Int): SpannableString {
    setSpan(AbsoluteSizeSpan(size), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

fun SpannableString.setBold(start: Int, end: Int): SpannableString {
    setSpan(StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

fun SpannableString.setItalic(start: Int, end: Int): SpannableString {
    setSpan(StyleSpan(Typeface.ITALIC), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

/**
 * 设置点击事件时，需要设置 textView.movementMethod = LinkMovementMethod.getInstance()
 */
fun SpannableString.setClick(start: Int, end: Int, color: Int, isUnderlineText: Boolean, listener: () -> Unit): SpannableString {
    setSpan(object : ClickableSpan() {
        override fun onClick(widget: View) {
            listener.invoke()
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = color
            ds.isUnderlineText = isUnderlineText
        }
    }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

