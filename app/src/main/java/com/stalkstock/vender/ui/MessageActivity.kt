package com.stalkstock.vender.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.stalkstock.vender.adapter.MessageAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.vender.Model.MessageList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.stalkstock.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.stalkstock.MyApplication
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.getPrefrence
import com.stalkstock.utils.socket.SocketManager
import kotlinx.android.synthetic.main.chat_fragment.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class MessageActivity : AppCompatActivity(),SocketManager.SocketInterface {
    var mcontext: Context? = null
    private var messageAdapter: MessageAdapter? = null
    var messagerecyclerview: RecyclerView? = null
    private var messageLists= ArrayList<MessageList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_fragment)
        mcontext = this
        MyApplication.getSocketManager().onRegister(this)
        val iv_back = findViewById<ImageView>(R.id.iv_back)
        iv_back.visibility = View.VISIBLE
        iv_back.setOnClickListener { onBackPressed() }
        messagerecyclerview = findViewById(R.id.messagerecyclerview)
        setAdapter()

        getMessageList()

    }

    private fun setAdapter() {
        messagerecyclerview?.layoutManager = LinearLayoutManager(mcontext)
        messageAdapter = MessageAdapter(this, messageLists)
        messagerecyclerview?.adapter = messageAdapter

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

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onSocketCall(event: String?, vararg args: Any?) {
        if (event != null) {
            Log.e("onSocketCall", event)
        }
        when (event) {
            SocketManager.GET_USER_LIST -> {
                val mObject = args[0] as JSONObject
                val list = mObject.get("body") as JsonArray
                Log.i("====",list.toString())
                if (mObject.length()>0) {
                    messagerecyclerview?.visibility=View.VISIBLE
                    tvNoMsgList.visibility = View.GONE
                    messageLists.clear()
                    for (i in 0 until list.size()) {
                        val jsonobj = mObject.getJSONObject(i.toString())
                        val gson = GsonBuilder().create()
                        val data = gson.fromJson(jsonobj.toString(), MessageList::class.java)
                        messageLists.add(data)
                        messageAdapter = MessageAdapter(mcontext!!,messageLists)
                        messagerecyclerview?.adapter = messageAdapter
                    }
                } else {
                    messagerecyclerview?.visibility=View.GONE
                    tvNoMsgList.visibility = View.VISIBLE
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
}