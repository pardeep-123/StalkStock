package com.stalkstock.utils.socket

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.stalkstock.MyApplication
import com.stalkstock.utils.others.GlobalVariables
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONArray
import org.json.JSONObject
import java.net.URISyntaxException
import java.util.*

class SocketManager  {
    private var mSocket: Socket? = null

    // Enter ON Method Name here
    private val mOnMethodName = "server_response"
    private var mSocketInterface: SocketInterface? = null
    private var observerList: MutableList<SocketInterface>? = null
    var isConnect = false

    fun isConnected(): Boolean {
        return mSocket != null && mSocket?.connected()!!
    }

    fun updateSocketInterface(mSocketInterface: SocketInterface?) {
        this.mSocketInterface = mSocketInterface
    }

    fun onRegister(observer: SocketInterface) {
        if (observerList != null && !observerList!!.contains(observer)) {
            observerList!!.add(observer)
        } else {
            observerList = ArrayList()
            observerList?.add(observer)
        }
    }

    fun unRegister(observer: SocketInterface) {
        try {
            if (observerList != null) {
                for (i in 0 until observerList?.size!! - 1) {
                    val model = observerList!![i]
                    if (model === observer) {
                        observerList?.remove(model)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Default Listener
     * Define what you want to do when connection is established
     */
    private val onConnect = Emitter.Listener { args ->
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            Log.e(TAG, "connect run")
            isConnect = true
            //                    if (mSocketInterface!=null)
//                        mSocketInterface.onSocketConnect(args);
            try {
                val user_id = MyApplication.instance.getString("globalID")
                if (user_id != null) {
                    if (user_id != "0") {
                        val jsonObject = JSONObject()
                        jsonObject.put("userId", user_id)
                        sendDataToServer(CONNECT_USER, jsonObject)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            for (observer in observerList!!) {
                observer.onSocketConnect(*args)
            }
        }
    }

    /**
     * Default Listener
     * Define what you want to do when connection is disconnected
     */
    private val onDisconnect = Emitter.Listener { args ->
        /**
         *
         * @param args args
         */
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            Log.i(TAG, "disconnected")
            Log.e(TAG, "disconnect")
            isConnect = false
            if (mSocketInterface != null) mSocketInterface!!.onSocketDisconnect(*args)
            for (observer in observerList!!) {
                observer.onSocketDisconnect(*args)
            }
        }
    }

    /**
     * Default Listener
     * Define what you want to do when there's a connection error
     */
    private val onConnectError =
        Emitter.Listener { args -> // Get a handler that can be used to post to the main thread
            Handler(Looper.getMainLooper()).post {
                for (observer in observerList!!) {
                    observer.onError("ERROR", *args)
                }
                Log.e(TAG, "Run" + args[0])
                Log.e(TAG, "Error connecting")
            }
        }

    /**
     * Call this method in onCreate and onResume
     */
    fun onConnect() {
        if (!mSocket!!.connected()) {
            mSocket!!.on(Socket.EVENT_CONNECT, onConnect)
            mSocket!!.on(Socket.EVENT_DISCONNECT, onDisconnect)
            mSocket!!.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
            mSocket!!.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
            mSocket!!.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
            mSocket!!.on(CONNECT_LISTNER, onConnectUserListner)
            mSocket!!.on(UPDATE_DRIVER_LOCATION, OnUPDATE_MY_LOCATIONListener)
            mSocket!!.on(TRACK_ORDER, onTrackOrderListener)
            mSocket!!.on(SEND_MESSAGE, onSendListner)
            mSocket!!.on(GET_CHAT, OnGetchatListener)
            mSocket!!.on(GET_USER_LIST, OnGetUserListener)
            mSocket!!.on(CLEAR_CHAT, onClearChatListener)
            //            mSocket.on(VendorOrderListener, OnVendorOrderListener);
            mSocket!!.on(driverOrderRequest, OnVendorOrderListener)
            mSocket!!.connect()
        }
    }

    /**
     * Call this method in onPause and onDestroy
     */
    fun onDisconnect() {
        isConnect = false
        mSocket!!.disconnect()
        mSocket!!.off(Socket.EVENT_CONNECT, onConnect)
        mSocket!!.off(Socket.EVENT_DISCONNECT, onDisconnect)
        mSocket!!.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
        mSocket!!.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
        mSocket!!.off(CONNECT_LISTNER, onConnectUserListner)
        mSocket!!.off(SEND_MESSAGE, onSendListner)
        mSocket!!.off(UPDATE_DRIVER_LOCATION, OnUPDATE_MY_LOCATIONListener)
        mSocket!!.off(TRACK_ORDER, onTrackOrderListener)
        mSocket!!.off(GET_USER_LIST, OnGetUserListener)
        //        mSocket.off(VendorOrderListener, OnVendorOrderListener);
        mSocket!!.off(driverOrderRequest, OnVendorOrderListener)
        mSocket!!.off(GET_CHAT, OnGetchatListener)
        mSocket!!.off(CLEAR_CHAT, onClearChatListener)
    }

    private val OnUPDATE_MY_LOCATIONListener = Emitter.Listener { args ->
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            val data = args[0] as JSONObject
            Log.e("LISTNERGETLIST", data.toString())
            //                    if (mSocketInterface != null) {
//                        Log.e("response_socket", args.toString());
//                        mSocketInterface.onSocketCall(CONNECT_LISTNER, args);
//                    }
            for (observer in observerList!!) {
                observer.onSocketCall(UPDATE_LOCATION_LISTENER, *args)
            }
        }
    }
    private val onTrackOrderListener = Emitter.Listener { args ->
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            val data = args[0] as JSONObject
            Log.e("LISTNERGETLIST", data.toString())
            //                    if (mSocketInterface != null) {
//                        Log.e("response_socket", args.toString());
//                        mSocketInterface.onSocketCall(CONNECT_LISTNER, args);
//                    }
            for (observer in observerList!!) {
                observer.onSocketCall(TRACK_ORDER_LISTENER, *args)
            }
        }
    }

    /*
     * On Method call backs from server
     * */
    private val socketServer =
        Emitter.Listener { args -> // Get a handler that can be used to post to the main thread
            Handler(Looper.getMainLooper()).post {
                val data = args[0] as JSONObject
                if (mSocketInterface != null) Log.e("response_socket", args.toString())
                mSocketInterface!!.onSocketCall(CONNECT_LISTNER, *args)
                for (observer in observerList!!) {
                    observer.onSocketCall(CONNECT_LISTNER, *args)
                }
            }
        }
    private val onConnectUserListner = Emitter.Listener { args ->
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post { //                    JSONObject data = (JSONObject) args[0];
            Log.e("LISTNERCONNECT", args[0].toString())
            for (observer in observerList!!) {
                observer.onSocketCall(CONNECT_LISTNER, *args)
            }
        }
    }
    private val onRecieveListner =
        Emitter.Listener { args -> // Get a handler that can be used to post to the main thread
            Handler(Looper.getMainLooper()).post {
                val data = args[0] as JSONObject
                Log.e("LISTNER_RecieveMessage", args[0].toString())
                for (observer in observerList!!) {
                    observer.onSocketCall(SEND_MESSAGE, *args)
                }
            }
        }
    private val onSendListner = Emitter.Listener { args ->
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            val data = args[0] as JSONObject
            Log.e("LISTNER_SendMessage", args[0].toString())
            //                    if (mSocketInterface != null) {
//                        Log.e("response_socket", args.toString());
//                        mSocketInterface.onSocketCall(CONNECT_LISTNER, args);
//                    }
            for (observer in observerList!!) {
                observer.onSocketCall(SEND_MESSAGE, *args)
            }
        }
    }
    private val OnSesndMessageListener = Emitter.Listener { args ->
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            val data = args[0] as JSONArray
            Log.e("LISTNERsend message", data.toString())
            //                    if (mSocketInterface != null) {
//                        Log.e("response_socket", args.toString());
//                        mSocketInterface.onSocketCall(CONNECT_LISTNER, args);
//                    }
            for (observer in observerList!!) {
                observer.onSocketCall(SEND_MESSAGE, *args)
            }
        }
    }
    private val OnGetchatListener = Emitter.Listener { args ->
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            val data = args[0] as JSONObject
            Log.e("LISTNERGETLIST", data.toString())
            //                    if (mSocketInterface != null) {
//                        Log.e("response_socket", args.toString());
//                        mSocketInterface.onSocketCall(CONNECT_LISTNER, args);
//                    }
            for (observer in observerList!!) {
                observer.onSocketCall(GET_CHAT, *args)
            }
        }
    }
    private val OnGetUserListener = Emitter.Listener { args ->
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            val data = args[0] as JSONObject
            Log.e("LISTNER_GETCHATLISt", data.toString())
            //                    if (mSocketInterface != null) {
//                        Log.e("response_socket", args.toString());
//                        mSocketInterface.onSocketCall(CONNECT_LISTNER, args);
//                    }
            for (observer in observerList!!) {
                observer.onSocketCall(GET_USER_LIST, *args)
            }
        }
    }
    private val OnVendorOrderListener = Emitter.Listener { args ->
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            val data = args[0] as JSONObject
            Log.e("VendorOrderListener", data.toString())
            //                    if (mSocketInterface != null) {
//                        Log.e("response_socket", args.toString());
//                        mSocketInterface.onSocketCall(CONNECT_LISTNER, args);
//                    }
            for (observer in observerList!!) {
                observer.onSocketCall(driverOrderRequest, *args)
            }
        }
    }
    private val onClearChatListener = Emitter.Listener { args ->
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            val data = args[0] as JSONObject
            Log.e("LISTNER_CAllStatusLISt", data.toString())
            //                    if (mSocketInterface != null) {
//                        Log.e("response_socket", args.toString());
//                        mSocketInterface.onSocketCall(CONNECT_LISTNER, args);
//                    }
            for (observer in observerList!!) {
                observer.onSocketCall(CLEAR_CHAT, *args)
            }
        }
    }

    /*
     * Send Data to server by use of socket
     * */
    fun sendDataToServer(methodName: String?, mObject: Any) {
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            mSocket!!.emit(methodName, mObject)
            Log.e(methodName, mObject.toString())
        }
    }

    /*
     * Interface for Socket Callbacks
     * */
    interface SocketInterface {
        fun onSocketCall(event: String?, vararg args: Any?)
        fun onSocketConnect(vararg args: Any?)
        fun onSocketDisconnect(vararg args: Any?)
        fun onError(event: String?, vararg args: Any?)
    }

    companion object {
        private var mSocketClass: SocketManager? = null
        private val TAG = SocketManager::class.java.canonicalName
        const val CONNECT_USER = "connectUser"
        const val CONNECT_LISTNER = "connectListener"
        const val SEND_MESSAGE = "sendMessage"
        const val GET_CHAT = "getChatMessageListing"

        const val GET_USER_LIST = "getChatListing"
        const val VendorOrderListener = "vendorOrder"
        const val driverOrderRequest = "driverOrderRequest"
        const val UPDATE_DRIVER_LOCATION = "update_driver_location"
        const val UPDATE_LOCATION_LISTENER = "get_driver_location"
        const val TRACK_ORDER = "track_order"
        const val TRACK_ORDER_LISTENER = "track_order"
        const val CLEAR_CHAT="clearChat"


        val socket: SocketManager?
            get() {
                if (mSocketClass == null) mSocketClass = SocketManager()
                return mSocketClass
            }
    }

    init {
        try {
            val options = IO.Options()
            options.reconnection = true
            options.reconnectionAttempts = Int.MAX_VALUE
            Log.e("Constants.BASE_URL", GlobalVariables.URL.SOCKET_URL)
            mSocket = IO.socket(GlobalVariables.URL.SOCKET_URL, options)
            if (observerList == null || observerList!!.size == 0) {
                observerList = ArrayList()
            }
           // onDisconnect()
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }
}