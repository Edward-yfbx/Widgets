package com.yfbx.widgets.util

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @Author: Edward
 * @Date: 2019-07-08
 * @Description:
 */
class GsonType(val raw: Class<*>, val args: Array<Type>? = arrayOf()) : ParameterizedType {

    override fun getRawType(): Type {
        return raw
    }

    override fun getOwnerType(): Type? {
        return null
    }

    override fun getActualTypeArguments(): Array<Type> {
        return args ?: arrayOf()
    }
}


inline fun <reified T> Gson.toList(json: String): ArrayList<T> {
    return fromJson(json, GsonType(ArrayList::class.java, arrayOf(T::class.java)))
}


fun JsonObject.getJsonObject(key: String): JsonObject? {
    val element = get(key)
    return if (element.isNull()) null else element.asJsonObject
}

fun JsonObject.getJsonArray(key: String): JsonArray? {
    val element = get(key)
    return if (element.isNull()) null else element.asJsonArray
}

fun JsonObject.getString(key: String): String? {
    val element = get(key)
    return if (element.isNull()) null else element.asString
}

fun JsonObject.getInt(key: String): Int? {
    val element = get(key)
    return if (element.isNull()) null else element.asInt
}

fun JsonObject.getFloat(key: String): Float? {
    val element = get(key)
    return if (element.isNull()) null else element.asFloat
}

fun JsonObject.getLong(key: String): Long? {
    val element = get(key)
    return if (element.isNull()) null else element.asLong
}

fun JsonObject.getDouble(key: String): Double? {
    val element = get(key)
    return if (element.isNull()) null else element.asDouble
}


fun JsonElement?.isNull(): Boolean {
    return this == null || this.isJsonNull
}

