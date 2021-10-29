package com.stalkstock.utils.others


import com.google.gson.Gson
import com.stalkstock.MyApplication


fun savePrefrence(key: String, value: Any) {
    val preference = MyApplication.getinstance().applicationContext.getSharedPreferences(
        GlobalVariables.SHARED_PREF.SHARED_NAME, 0
    )
    val editor = preference.edit()

    when (value) {
        is String -> editor.putString(key, value)
        is Boolean -> editor.putBoolean(key, value)
        is Int -> editor.putInt(key, value)
    }
    editor.apply()
}

inline fun <reified T> getPrefrence(key: String, deafultValue: T): T {
    val preference = MyApplication.getinstance().applicationContext.getSharedPreferences(
        GlobalVariables.SHARED_PREF.SHARED_NAME, 0
    )
    return when (T::class) {
        String::class -> preference.getString(key, deafultValue as String) as T
        Boolean::class -> preference.getBoolean(key, deafultValue as Boolean) as T
        Int::class -> preference.getInt(key, deafultValue as Int) as T
        else -> {
            " " as T
        }
    }

}

inline fun <reified T> savePrefObject(key: String, obj: T) {
    savePrefrence(key, Gson().toJson(obj))
}

inline fun <reified T> getprefObject(key: String): T {
    return Gson().fromJson(getPrefrence(key, ""), T::class.java)
}

fun clearPrefrences() {
    val preference = MyApplication.getinstance().applicationContext.getSharedPreferences(
        GlobalVariables.SHARED_PREF.SHARED_NAME, 0
    )
    val editor = preference.edit()
    editor.clear()
    editor.apply()
}