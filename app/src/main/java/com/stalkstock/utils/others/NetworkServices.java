package com.stalkstock.utils.others;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;


public class NetworkServices extends Service implements ConnectivityReceiver.ConnectivityReceiverListener
{
    Context context;
    boolean isConnect = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void NetworkServices(Context context){
        this.context= context;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        //AppController.getInstance().setConnectivityListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected, Context context) {

        this.context=context;
        this.isConnect=isConnected;

        if(isConnected)
        {}
    }
}
