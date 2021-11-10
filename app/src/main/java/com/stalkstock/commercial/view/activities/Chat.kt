package com.stalkstock.commercial.view.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.stalkstock.MyApplication
import com.stalkstock.commercial.view.adapters.ChatAdapter
import com.stalkstock.R
import com.stalkstock.utils.others.CommonMethods
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.getPrefrence
import com.stalkstock.utils.socket.SocketManager
import com.stalkstock.vender.Model.MessageList
import com.stalkstock.vender.adapter.MessageAdapter
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.chat_fragment.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class Chat : AppCompatActivity(), View.OnClickListener,SocketManager.SocketInterface {

    var click = ""
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage=""
    var chatList : ArrayList<MessageList> = ArrayList()
    lateinit var chatAdapter: ChatAdapter
    var chatId=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        MyApplication.getSocketManager().onRegister(this)
        init()
        getIntentData()
        setAdapter()
        CommonMethods.hideKeyboard(this@Chat,rvChat)
        getChatList()
    }

    private fun getIntentData() {
        chatId= intent.getStringExtra("id").toString()
    }

    private fun getChatList() {
        val userId = getPrefrence(GlobalVariables.SHARED_PREF_DRIVER.id, 0)

        val jsonObject = JSONObject()
        jsonObject.put("userId", userId)
        jsonObject.put("chatId", chatId.toInt())
        jsonObject.put("offset", "1")
        jsonObject.put("limit", "500")
        Log.e(SocketManager.GET_CHAT, jsonObject.toString())
        SocketManager.socket?.sendDataToServer(SocketManager.GET_CHAT, jsonObject)
    }

    private fun setAdapter() {

        messagerecyclerview?.layoutManager = LinearLayoutManager(this)
        chatAdapter = ChatAdapter(this, chatList)
        rvChat?.adapter = chatAdapter

    }

    private fun selectImage(ivProduct: ImageView, type:String) {
        Album.image(this)
            .singleChoice()
            .camera(true)
            .columnCount(4)
            .widget(
                Widget.newDarkBuilder(this)
                    .title(getString(R.string.app_name))
                    .build()
            )
            .onResult { result ->
                mAlbumFiles.addAll(result)
                if (type == "1")
                {
                    firstimage = result[0].path
                }
            }
            .onCancel {

            }
            .start()
    }

    private  fun init(){
        iv_backChat.setOnClickListener(this)
        iv_dotMenu.setOnClickListener(this)
        ivSend.setOnClickListener(this)
        tvClearChat.setOnClickListener(this)
        ivAttach.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.iv_backChat->{
                onBackPressed()
            }
            R.id.ivAttach ->{
                mAlbumFiles = java.util.ArrayList()
                mAlbumFiles.clear()
                selectImage(ivAttach,"1")
            }
            R.id.iv_dotMenu->{
                if(click == ""){
                    clDots.visibility = View.VISIBLE
                    click = "1"
                }else{
                    clDots.visibility = View.GONE
                    click = ""
                }

            }
            R.id.ivSend->{
                if(etMessage.text.toString().isNotEmpty())
                {
//                    chatList.add(ChatData(etMessage.text.toString(),"17:00 PM","a","me"))
//                    chatAdapter.notifyItemInserted(chatList.size-1)
//                    rvChat.smoothScrollToPosition(chatList.size-1)
//                    etMessage.text.clear()
                }
                else
                {
                    Toast.makeText(this,"Nothing to send", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.tvClearChat->{
                Log.e("fdgdfsgdfsg","jklfh;jklagdfk")
                clearChat()
            }
        }
    }


    fun clearChat(){

        val dialogSuccessful = Dialog(this, R.style.Theme_Dialog)
        dialogSuccessful.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogSuccessful.setContentView(R.layout.delete_successfully_alertaddress)
        dialogSuccessful.setCancelable(false)
        Objects.requireNonNull<Window>(dialogSuccessful.window).setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialogSuccessful.setCanceledOnTouchOutside(false)
        dialogSuccessful.window!!.setGravity(Gravity.CENTER)

        val btn_yes = dialogSuccessful.findViewById<Button>(R.id.btn_yes)
        val tvMsg = dialogSuccessful.findViewById<TextView>(R.id.tv_msg)
        val btn_no = dialogSuccessful.findViewById<Button>(R.id.btn_no)


        tvMsg.text = "Are you sure you want to clear this Chat?"

        btn_yes.setOnClickListener { dialogSuccessful.dismiss()
            finish()}

        btn_no.setOnClickListener { dialogSuccessful.dismiss() }

        dialogSuccessful.show()
    }

    data class ChatData(var message: String = "", var time : String = "", var image: String = "",var type : String = "")

    override fun onSocketCall(event: String?, vararg args: Any?) {

        if (event != null) {
            Log.e("onSocketCall", event)
        }
        when (event) {
            SocketManager.GET_CHAT -> {
                val mObject = args[0] as JSONObject
                val list = mObject.get("body") as JsonArray
                Log.i("====",list.toString())
                if (mObject.length()>0) {
                    chatList.clear()
                    for (i in 0 until list.size()) {
                        val jsonobj = mObject.getJSONObject(i.toString())
                        val gson = GsonBuilder().create()
                        val data = gson.fromJson(jsonobj.toString(), MessageList::class.java)
                        chatList.add(data)
                        chatAdapter = ChatAdapter(this,chatList)
                        rvChat?.adapter = chatAdapter
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