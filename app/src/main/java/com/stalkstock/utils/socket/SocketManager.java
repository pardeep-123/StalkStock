package com.stalkstock.utils.socket;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.stalkstock.MyApplication;
import com.stalkstock.utils.others.GlobalVariables;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketManager {

    private static SocketManager mSocketClass = null;
    private static final String TAG = SocketManager.class.getCanonicalName();
    private Socket mSocket;
    // Enter ON Method Name here
    private String mOnMethodName = "server_response";
    private SocketInterface mSocketInterface = null;
    private List<SocketInterface> observerList;
    public static final String CONNECT_USER = "connectUser";
    public static final String CONNECT_LISTNER = "connectListener";

    public static final String SEND_MESSAGE = "send_message";
    public static final String RECIEVE_MESSAGE = "new_message";

    public static final String GET_CHAT = "get_message";
    public static final String GET_USER_LIST = "chat_listing";
    public static final String VendorOrderListener = "vendorOrder";

    public static final String driverOrderRequest = "driverOrderRequest";

    public static final String UPDATE_DRIVER_LOCATION = "update_driver_location";
    public static final String UPDATE_LOCATION_LISTENER = "get_driver_location";

    public static final String TRACK_ORDER = "track_order";
    public static final String TRACK_ORDER_LISTENER = "track_order";

    public static final String CALL_STATUS = "call_status";



    private SocketManager() {
        try {
            IO.Options options = new IO.Options();
            options.reconnection = true;
            options.reconnectionAttempts = Integer.MAX_VALUE;
            Log.e("Constants.BASE_URL", GlobalVariables.URL.SOCKET_URL);
            mSocket = IO.socket(GlobalVariables.URL.SOCKET_URL, options);
            if (observerList == null || observerList.size() == 0) {
                observerList = new ArrayList<>();
            }
            onDisconnect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static SocketManager getSocket() {
        if (mSocketClass == null)
            mSocketClass = new SocketManager();
        return mSocketClass;
    }

    public Boolean isConnected = false;

    public boolean isConnected() {
        return mSocket != null && mSocket.connected();
    }

    public void updateSocketInterface(SocketInterface mSocketInterface) {
        this.mSocketInterface = mSocketInterface;
    }

    public void onRegister(SocketInterface observer) {
        if (observerList != null && !observerList.contains(observer)) {
            observerList.add(observer);
        } else {
            observerList = new ArrayList<>();
            observerList.add(observer);
        }
    }

    public void unRegister(SocketInterface observer) {
        if (observerList != null) {
            for (int i = 0; i < observerList.size(); i++) {
                SocketInterface model = observerList.get(i);
                if (model == observer) {
                    observerList.remove(model);
                }
            }
        }
    }


    /**
     * Default Listener
     * Define what you want to do when connection is established
     */
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            // Get a handler that can be used to post to the main thread
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    Log.e(TAG, "connect run");
                    isConnected = true;
//                    if (mSocketInterface!=null)
//                        mSocketInterface.onSocketConnect(args);
                    try {
                        String user_id = MyApplication.instance.getString("globalID");
                        if (user_id != null) {
                            if (!user_id.equals("0")) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("userId", user_id);
                                sendDataToServer(CONNECT_USER, jsonObject);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for (SocketInterface observer : observerList) {
                        observer.onSocketConnect(args);
                    }
                }
            });
        }
    };
    /**
     * Default Listener
     * Define what you want to do when connection is disconnected
     */
    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        /**
         *
         * @param args args
         */
        @Override
        public void call(final Object... args) {
            // Get a handler that can be used to post to the main thread
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "disconnected");
                    Log.e(TAG, "disconnect");
                    isConnected = false;
                    if (mSocketInterface!=null)
                        mSocketInterface.onSocketDisconnect(args);

                    for (SocketInterface observer : observerList) {
                        observer.onSocketDisconnect(args);
                    }
                }
            });
        }
    };

    /**
     * Default Listener
     * Define what you want to do when there's a connection error
     */
    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            // Get a handler that can be used to post to the main thread
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    for (SocketInterface observer : observerList) {
                        observer.onError("ERROR", args);
                    }
                    Log.e(TAG, "Run" + args[0]);
                    Log.e(TAG, "Error connecting");
                }
            });
        }
    };

    /**
     * Call this method in onCreate and onResume
     */
    public void onConnect() {
        if (!mSocket.connected()) {

            mSocket.on(Socket.EVENT_CONNECT, onConnect);
            mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
            mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
            mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
            mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
            mSocket.on(CONNECT_LISTNER, onConnectUserListner);
            mSocket.on(RECIEVE_MESSAGE, onRecieveListner);

            mSocket.on(UPDATE_DRIVER_LOCATION, OnUPDATE_MY_LOCATIONListener);
            mSocket.on(TRACK_ORDER, onTrackOrderListener);
            mSocket.on(SEND_MESSAGE, onSendListner);
            mSocket.on(GET_CHAT, OnGetchatListener);
            mSocket.on(GET_USER_LIST, OnGetUserListener);
//            mSocket.on(VendorOrderListener, OnVendorOrderListener);
            mSocket.on(driverOrderRequest, OnVendorOrderListener);
            mSocket.on(CALL_STATUS, OnCallStatusListener);
            mSocket.connect();
        }
    }

    /**
     * Call this method in onPause and onDestroy
     */
    public void onDisconnect() {
        isConnected = false;
        mSocket.disconnect();
        mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(CONNECT_LISTNER, onConnectUserListner);
        mSocket.off(RECIEVE_MESSAGE, onRecieveListner);
        mSocket.off(SEND_MESSAGE, onSendListner);
        mSocket.off(UPDATE_DRIVER_LOCATION, OnUPDATE_MY_LOCATIONListener);
        mSocket.off(TRACK_ORDER, onTrackOrderListener);

        mSocket.off(GET_USER_LIST, OnGetUserListener);
//        mSocket.off(VendorOrderListener, OnVendorOrderListener);
        mSocket.off(driverOrderRequest, OnVendorOrderListener);
        mSocket.off(GET_CHAT, OnGetchatListener);
        mSocket.off(CALL_STATUS, OnCallStatusListener);
    }

    private Emitter.Listener OnUPDATE_MY_LOCATIONListener = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            // Get a handler that can be used to post to the main thread
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.e("LISTNERGETLIST", data.toString());
//                    if (mSocketInterface != null) {
//                        Log.e("response_socket", args.toString());
//                        mSocketInterface.onSocketCall(CONNECT_LISTNER, args);
//                    }

                    for (SocketInterface observer : observerList) {
                        observer.onSocketCall(UPDATE_LOCATION_LISTENER, args);
                    }
                }
            });
        }
    };

    private Emitter.Listener onTrackOrderListener = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            // Get a handler that can be used to post to the main thread
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.e("LISTNERGETLIST", data.toString());
//                    if (mSocketInterface != null) {
//                        Log.e("response_socket", args.toString());
//                        mSocketInterface.onSocketCall(CONNECT_LISTNER, args);
//                    }

                    for (SocketInterface observer : observerList) {
                        observer.onSocketCall(TRACK_ORDER_LISTENER, args);
                    }
                }
            });
        }
    };


    /*
     * On Method call backs from server
     * */
    private Emitter.Listener socketServer = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            // Get a handler that can be used to post to the main thread
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    if (mSocketInterface != null)
                        Log.e("response_socket", args.toString());
                    mSocketInterface.onSocketCall(CONNECT_LISTNER, args);

                    for (SocketInterface observer : observerList) {
                        observer.onSocketCall(CONNECT_LISTNER, args);
                    }
                }
            });
        }
    };

    private Emitter.Listener onConnectUserListner = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            // Get a handler that can be used to post to the main thread
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
//                    JSONObject data = (JSONObject) args[0];
                    Log.e("LISTNERCONNECT", args[0].toString());


                    for (SocketInterface observer : observerList) {
                        observer.onSocketCall(CONNECT_LISTNER, args);
                    }
                }
            });
        }
    };

    private Emitter.Listener onRecieveListner = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            // Get a handler that can be used to post to the main thread
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.e("LISTNER_RecieveMessage", args[0].toString());


                    for (SocketInterface observer : observerList) {
                        observer.onSocketCall(RECIEVE_MESSAGE, args);
                    }
                }
            });
        }
    };

    private Emitter.Listener onSendListner = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            // Get a handler that can be used to post to the main thread
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.e("LISTNER_SendMessage", args[0].toString());
//                    if (mSocketInterface != null) {
//                        Log.e("response_socket", args.toString());
//                        mSocketInterface.onSocketCall(CONNECT_LISTNER, args);
//                    }

                    for (SocketInterface observer : observerList) {
                        observer.onSocketCall(SEND_MESSAGE, args);
                    }
                }
            });
        }
    };

    private Emitter.Listener OnSesndMessageListener = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            // Get a handler that can be used to post to the main thread
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONArray data = (JSONArray) args[0];
                    Log.e("LISTNERsend message", data.toString());
//                    if (mSocketInterface != null) {
//                        Log.e("response_socket", args.toString());
//                        mSocketInterface.onSocketCall(CONNECT_LISTNER, args);
//                    }

                    for (SocketInterface observer : observerList) {
                        observer.onSocketCall(SEND_MESSAGE, args);
                    }
                }
            });
        }
    };


    private Emitter.Listener OnGetchatListener = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            // Get a handler that can be used to post to the main thread
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONArray data = (JSONArray) args[0];
                    Log.e("LISTNERGETLIST", data.toString());
//                    if (mSocketInterface != null) {
//                        Log.e("response_socket", args.toString());
//                        mSocketInterface.onSocketCall(CONNECT_LISTNER, args);
//                    }

                    for (SocketInterface observer : observerList) {
                        observer.onSocketCall(GET_CHAT, args);
                    }
                }
            });
        }
    };

    private Emitter.Listener OnGetUserListener = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            // Get a handler that can be used to post to the main thread
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONArray data = (JSONArray) args[0];
                    Log.e("LISTNER_GETCHATLISt", data.toString());
//                    if (mSocketInterface != null) {
//                        Log.e("response_socket", args.toString());
//                        mSocketInterface.onSocketCall(CONNECT_LISTNER, args);
//                    }

                    for (SocketInterface observer : observerList) {
                        observer.onSocketCall(GET_USER_LIST, args);
                    }
                }
            });
        }
    };

    private Emitter.Listener OnVendorOrderListener = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            // Get a handler that can be used to post to the main thread
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.e("VendorOrderListener", data.toString());
//                    if (mSocketInterface != null) {
//                        Log.e("response_socket", args.toString());
//                        mSocketInterface.onSocketCall(CONNECT_LISTNER, args);
//                    }

                    for (SocketInterface observer : observerList) {
                        observer.onSocketCall(driverOrderRequest, args);
                    }
                }
            });
        }
    };

    private Emitter.Listener OnCallStatusListener = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            // Get a handler that can be used to post to the main thread
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.e("LISTNER_CAllStatusLISt", data.toString());
//                    if (mSocketInterface != null) {
//                        Log.e("response_socket", args.toString());
//                        mSocketInterface.onSocketCall(CONNECT_LISTNER, args);
//                    }

                    for (SocketInterface observer : observerList) {
                        observer.onSocketCall(CALL_STATUS, args);
                    }
                }
            });
        }
    };

    /*
     * Send Data to server by use of socket
     * */
    public void sendDataToServer(final String methodName, final Object mObject) {
        // Get a handler that can be used to post to the main thread
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mSocket.emit(methodName, mObject);
                Log.e(methodName, mObject.toString());
            }
        });
    }

    /*
     * Interface for Socket Callbacks
     * */
    public interface SocketInterface {
        void onSocketCall(String event, Object... args);

        void onSocketConnect(Object... args);

        void onSocketDisconnect(Object... args);

        void onError(String event, Object... args);
    }

}
