package com.stalkstock.commercial.view.fragments.home.message

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.getPrefrence
import com.stalkstock.utils.socket.SocketManager
import com.stalkstock.vender.Model.MessageList
import com.stalkstock.vender.adapter.MessageAdapter
import kotlinx.android.synthetic.main.fragment_messages.*
import org.json.JSONArray
import org.json.JSONObject

class MessagesFragment : Fragment(), View.OnClickListener,SocketManager.SocketInterface{
  //  var listner: CommunicationListner?=null
  var mcontext: Context? = null
    var handler: Handler?=null
    private var messageAdapter: MessageAdapter? = null
    private var messageLists= ArrayList<MessageList>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mcontext=requireActivity()
        MyApplication.getSocketManager().onRegister(this)
        setAdapter()
        getMessageList()

    }
    private fun getMessageList() {
        val userId = getPrefrence(GlobalVariables.SHARED_PREF_DRIVER.id, 0)

        val jsonObject = JSONObject()
        jsonObject.put("userId", userId)
        jsonObject.put("offset", "0")
        jsonObject.put("limit", "100")
        Log.e(SocketManager.GET_USER_LIST, jsonObject.toString())
        SocketManager.socket?.sendDataToServer(SocketManager.GET_USER_LIST, jsonObject)
    }
    private fun setAdapter() {
        rv_messagesList.layoutManager = LinearLayoutManager(mcontext)
        messageAdapter = MessageAdapter(mcontext!!, messageLists)
        rv_messagesList.adapter = messageAdapter
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){

        }
    }

    override fun onSocketCall(event: String?, vararg args: Any?) {
        if (event != null) {
            Log.e("onSocketCall", event)
        }
        when (event) {
            SocketManager.GET_USER_LIST -> {
                messageLists.clear()
                val mObject = args[0] as JSONObject
                val list = mObject.get("body") as JSONArray
                Log.i("====",list.toString())
                if (list.length()>0) {
                    rv_messagesList.visibility=View.VISIBLE
                    tvNoMsgData.visibility = View.GONE

                    for (i in 0 until list.length()) {
                        val jsonobj = list.getJSONObject(i)
                        val gson = GsonBuilder().create()
                        val data = gson.fromJson(jsonobj.toString(), MessageList::class.java)
                        messageLists.add(data)
                        messageAdapter = MessageAdapter(mcontext!!,messageLists)
                        rv_messagesList.adapter = messageAdapter
                    }
                } else {
                    rv_messagesList.visibility=View.GONE
                    tvNoMsgData.visibility = View.VISIBLE
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

    override fun onResume() {
        super.onResume()
        MyApplication.getSocketManager().onRegister(this)
    }

}