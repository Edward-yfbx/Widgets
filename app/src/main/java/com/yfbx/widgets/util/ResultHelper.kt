package com.yfbx.widgets.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import java.io.Serializable

/**
 * Author: Edward
 * Date: 2020-04-13
 * Description:
 */

fun FragmentActivity.startForResult(intent: Intent, callback: (ActivityResult) -> Unit) {
    prepareCall(ActivityResultContracts.StartActivityForResult(), callback).launch(intent)
}


fun FragmentActivity.permissionFor(permission: String, callback: (Boolean) -> Unit) {
    prepareCall(ActivityResultContracts.RequestPermission(), callback).launch(permission)
}

fun FragmentActivity.permissionFor(vararg permission: String, callback: (Map<String, Boolean>) -> Unit) {
    prepareCall(ActivityResultContracts.RequestPermissions(), callback).launch(permission)
}


fun FragmentActivity.takePhoto(callback: (Drawable?) -> Unit) {
    prepareCall(DrawableResult(), callback).launch(null)
}

/**
 * 自定义
 */
class DrawableResult : ActivityResultContract<Void?, Drawable?>() {

    override fun createIntent(context: Context, input: Void?): Intent {
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Drawable? {
        return if (resultCode == Activity.RESULT_OK && intent != null) {
            val bitmap = intent.getParcelableExtra<Bitmap>("data")
            BitmapDrawable(Resources.getSystem(), bitmap)
        } else {
            null
        }
    }
}


fun Intent.putArgs(vararg params: Pair<String, Any?>): Intent {
    params.forEach {
        when (val value = it.second) {
            null -> putExtra(it.first, null as Serializable?)
            is Int -> putExtra(it.first, value)
            is Long -> putExtra(it.first, value)
            is CharSequence -> putExtra(it.first, value)
            is String -> putExtra(it.first, value)
            is Float -> putExtra(it.first, value)
            is Double -> putExtra(it.first, value)
            is Char -> putExtra(it.first, value)
            is Short -> putExtra(it.first, value)
            is Boolean -> putExtra(it.first, value)
            is Serializable -> putExtra(it.first, value)
            is Bundle -> putExtra(it.first, value)
            is Parcelable -> putExtra(it.first, value)
            is Array<*> -> when {
                value.isArrayOf<CharSequence>() -> putExtra(it.first, value)
                value.isArrayOf<String>() -> putExtra(it.first, value)
                value.isArrayOf<Parcelable>() -> putExtra(it.first, value)
                else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
            }
            is IntArray -> putExtra(it.first, value)
            is LongArray -> putExtra(it.first, value)
            is FloatArray -> putExtra(it.first, value)
            is DoubleArray -> putExtra(it.first, value)
            is CharArray -> putExtra(it.first, value)
            is ShortArray -> putExtra(it.first, value)
            is BooleanArray -> putExtra(it.first, value)
            else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
        }
    }
    return this
}