package com.stalkstock.vender.fragment

import android.content.Context
import com.stalkstock.vender.adapter.MessageAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.vender.Model.MessageList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.stalkstock.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.stalkstock.MyApplication
import com.stalkstock.driver.models.NewOrderResponse
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.getPrefrence
import com.stalkstock.utils.socket.SocketManager
import kotlinx.android.synthetic.main.fragment_h_ome.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Array
import java.util.ArrayList

class MessageFragment : Fragment(), SocketManager.SocketInterface {
    var mcontext: Context? = null
    private var messageAdapter: MessageAdapter? = null
    var messagerecyclerview: RecyclerView? = null
    private var messageLists=  ArrayList<MessageList>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.chat_fragment, container, false)
        mcontext = activity
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MyApplication.getSocketManager().onRegister(this)

        messagerecyclerview = view.findViewById(R.id.messagerecyclerview)
        messagerecyclerview?.layoutManager = LinearLayoutManager(mcontext)
        messagerecyclerview?.adapter = messageAdapter

        getMessageList()
    }

    private fun getMessageList() {
        val userId = getPrefrence(GlobalVariables.SHARED_PREF_DRIVER.id, 0)

        val jsonObject = JSONObject()
        jsonObject.put("userId", userId)
        jsonObject.put("offset", "1")
        jsonObject.put("limit", "10")
        Log.e(SocketManager.GET_USER_LIST, jsonObject.toString())
        SocketManager.socket?.sendDataToServer(SocketManager.GET_USER_LIST, jsonObject)
    }

    override fun onSocketCall(event: String?, vararg args: Any?) {
        if (event != null) {
            Log.e("onSocketCall", event)
        }
        when (event) {
            SocketManager.GET_USER_LIST -> {
                val mObject = args[0] as JSONArray
                Log.i("====",mObject.length().toString())
                if (mObject.length()>0) {

                    messageLists.clear()
                    for (i in 0 until mObject.length()) {
                        val jsonobj = mObject.getJSONObject(i)
                        val gson = GsonBuilder().create()
                        val data = gson.fromJson(jsonobj.toString(), MessageList::class.java)
                        messageLists.add(data)
                        messageAdapter = MessageAdapter(mcontext!!,messageLists)
                        messagerecyclerview?.adapter = messageAdapter
                    }
                } else {
                 //   tvNotFound.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onSocketConnect(vararg args: Any?) {

    }

    override fun onSocketDisconnect(vararg args: Any?) {

    }

    override fun onError(event: String?, vararg args: Any?) {

    }
    override fun onDestroy() {
        super.onDestroy()
        MyApplication.getSocketManager().unRegister(this)
    }


}