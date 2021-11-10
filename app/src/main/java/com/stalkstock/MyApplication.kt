package com.stalkstock

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.preference.PreferenceManager
import android.util.Log
import com.google.android.libraries.places.api.Places
import com.stalkstock.api.RestApiInterface
import com.stalkstock.api.ServiceGenerator
import com.stalkstock.utils.extention.checkStringNull
import com.stalkstock.utils.others.AppLifecycleHandler
import com.stalkstock.utils.others.AppLifecycleHandler.AppLifecycleDelegates
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.MediaLoader
import com.stalkstock.utils.socket.SocketManager
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import io.socket.client.IO
import io.socket.client.Socket
import java.util.*


class MyApplication : Application(), AppLifecycleDelegates {

    companion object {
        const val PREF_TOKEN = "dummy"

        var preferences: SharedPreferences? = null
        var prefToken: SharedPreferences? = null
        var editor: SharedPreferences.Editor? = null
        var editorToken: SharedPreferences.Editor? = null
        lateinit var instance: MyApplication

        fun getinstance(): MyApplication {
            return instance!!
        }

        private var mSocketManager: SocketManager? = null

        fun getSocketManager(): SocketManager {
            if (mSocketManager == null) {
                mSocketManager = SocketManager.socket
                return mSocketManager!!
            } else {
                return mSocketManager!!
            }
        }

//        fun hasNetwork(): Boolean {
//            return AppController.mInstance.checkIfHasNetwork()
//        }


    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        //   MultiDex.install(this);
    }

    override fun onCreate() {
        super.onCreate()
        instance = this


        //mSocketManager = getSocketManager();
        val lifecycleHandler = AppLifecycleHandler(this)
        registerLifecycleHandler(lifecycleHandler)
        //   FirebaseApp.initializeApp(this);
        //   FirebaseApp.initializeApp(this);
        initializePreferences()
        initializePreferencesToken()
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, resources.getString(R.string.maps_api_key))
        }
        Album.initialize(
            AlbumConfig.newBuilder(this)
                .setAlbumLoader(MediaLoader())
                .setLocale(Locale.getDefault())
                .build()
        )

        getSocketManager()

//        FirebaseApp.initializeApp(this)
    }


    fun checkIfHasNetwork(): Boolean {
        val cm =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

    private fun initializePreferences() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = preferences?.edit()
    }

    // initialize shared preferences for token
    private fun initializePreferencesToken() {
        prefToken =
            getSharedPreferences(PREF_TOKEN, Context.MODE_PRIVATE)
        editorToken = prefToken?.edit()
    }

    fun setString(key: String?, value: String?) {
        editor!!.putString(key, value)
        editor!!.commit()
    }

    fun getString(key: String?): String? {
        return preferences!!.getString(key, "")
    }

    fun setInt(key: String?, value: Int) {
        editor!!.putInt(key, value)
        editor!!.commit()
    }

    fun getInt(key: String?): Int {
        return preferences!!.getInt(key, 0)
    }


    fun clearData() {
        preferences!!.edit().clear().commit()
    }

    fun setDeviceToken(key: String?, value: String?) {
        editorToken!!.putString(key, value)
        editorToken!!.commit()
    }

    fun getDeviceToken(key: String?, def_value: String?): String? {
        return prefToken!!.getString(key, def_value)
    }


    override fun onAppForegrounded() {
        val string = getinstance().getString("globalID")
        if (!checkStringNull(string)) {
            if (!mSocketManager!!.isConnected()) {
//                mSocketManager!!.onConnect()
                mSocketManager!!.onConnect()
            }
        }
    }

    override fun onAppBackgrounded() {
        mSocketManager!!.onDisconnect()
    }


    var restApiInterface: RestApiInterface? = null
    fun provideAuthservice(): RestApiInterface {
        if (restApiInterface == null) {
            restApiInterface = ServiceGenerator.createService(RestApiInterface::class.java)
            return restApiInterface!!
        } else {
            return restApiInterface!!
        }

    }


    private fun registerLifecycleHandler(lifeCycleHandler: AppLifecycleHandler) {
        registerActivityLifecycleCallbacks(lifeCycleHandler)
        registerComponentCallbacks(lifeCycleHandler)
    }

    override fun onTerminate() {
        super.onTerminate()
    }


}
