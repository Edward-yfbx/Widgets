package com.yfbx.widgets.util

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author: Edward
 * @Date: 2019-07-25
 * @Description:
 */


/**
 * Format Number
 * 10,000.00
 */
fun Number.toMoney(): String {
    return DecimalFormat(",##0.00").format(this)
}

/**
 * Format Number
 * 10000.00
 */
fun Number.format(): String {
    return DecimalFormat("#0.00").format(this)
}

/**
 * 日期格式化
 */
fun Date.format(format: String): String {
    return SimpleDateFormat(format, Locale.getDefault()).format(this)
}

/**
 * 毫秒值 格式化为 日期
 */
fun Long.format(format: String): String {
    return SimpleDateFormat(format, Locale.getDefault()).format(this)
}

/**
 * 日期字符串 转换成日期
 */
fun String.toDate(format: String): Date {
    return SimpleDateFormat(format, Locale.getDefault()).parse(this)
}

