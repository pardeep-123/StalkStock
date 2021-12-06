package com.stalkstock.commercial.view.activities

import android.app.Dialog
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
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.commercial.view.adapters.ChatAdapter
import com.stalkstock.utils.others.CommonMethods
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.getPrefrence
import com.stalkstock.utils.socket.SocketManager
import com.stalkstock.vender.Model.MessageList
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.chat_fragment.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class Chat : AppCompatActivity(), View.OnClickListener, SocketManager.SocketInterface {

    var click = ""
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage = ""
    var chatList: ArrayList<MessageList> = ArrayList()
    var chatAdapter: ChatAdapter? = null
    var chatId = ""
    var screen_type = ""
    var user2Id = 0
    var bidId = 0
    var paramName = ""
    var userName = ""
    var userImage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatAdapter = ChatAdapter(
            this,
            chatList,
            getPrefrence(GlobalVariables.SHARED_PREF_DRIVER.id, 0).toString()
        )
        rvChat.adapter = chatAdapter

        init()
        getIntentData()
        CommonMethods.hideKeyboard(this@Chat, rvChat)
        getChatList()
    }

    private fun getIntentData() {
        screen_type = intent.getStringExtra("screen_type").toString()
        chatId = intent.getStringExtra("chatId").toString()
        userName = intent.getStringExtra("userName").toString()
        userImage = intent.getStringExtra("userImage").toString()
        user2Id = intent.getIntExtra("userId", 0)
        bidId = intent.getStringExtra("id").toString().toInt()
        paramName = intent.getStringExtra("param_name").toString()
        tv_nameOfChatPerson.text = userName
        Glide.with(this).load(userImage).into(iv_userImageChat)
    }

    private fun getChatList() {
        val userId = getPrefrence(GlobalVariables.SHARED_PREF_DRIVER.id, 0)

        val jsonObject = JSONObject()
        jsonObject.put("userId", user2Id)
        jsonObject.put("chatId", chatId.toInt())
        jsonObject.put("offset", "0")
        jsonObject.put("limit", "500")
        Log.e(SocketManager.GET_CHAT, jsonObject.toString())
        SocketManager.socket?.sendDataToServer(SocketManager.GET_CHAT, jsonObject)
    }

    private fun selectImage(ivProduct: ImageView, type: String) {
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
                if (type == "1") {
                    firstimage = result[0].path
                }
            }
            .onCancel {

            }
            .start()
    }

    private fun init() {
        iv_backChat.setOnClickListener(this)
        iv_dotMenu.setOnClickListener(this)
        ivSend.setOnClickListener(this)
        tvClearChat.setOnClickListener(this)
        ivAttach.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_backChat -> {
                onBackPressed()
            }
            R.id.ivAttach -> {
                mAlbumFiles = java.util.ArrayList()
                mAlbumFiles.clear()
                selectImage(ivAttach, "1")
            }
            R.id.iv_dotMenu -> {
                if (click == "") {
                    clDots.visibility = View.VISIBLE
                    click = "1"
                } else {
                    clDots.visibility = View.GONE
                    click = ""
                }

            }
            R.id.ivSend -> {

                if (etMessage.text.toString().trim().isEmpty()) {
                    Toast.makeText(this, "Please enter a message!!", Toast.LENGTH_LONG).show()
                } else {

                    val userId = getPrefrence(GlobalVariables.SHARED_PREF_DRIVER.id, 0)
                    val jsonObject = JSONObject()
                    jsonObject.put("senderId", userId)
                    if (paramName == "bidId") {
                        jsonObject.put("bidId", bidId)
                    } else {
                        jsonObject.put("orderId", bidId)
                    }
                    jsonObject.put("receiverId", user2Id.toInt())
                    jsonObject.put("messageType", 0)       // 1 for image, 2 for text
                    jsonObject.put("message", etMessage.text.toString().trim())
                    SocketManager.socket?.sendDataToServer(SocketManager.SEND_MESSAGE, jsonObject)
                    etMessage.setText("")
                    etMessage.setSelection(0)
                }
            }

            R.id.tvClearChat -> {
                Log.e("fdgdfsgdfsg", "jklfh;jklagdfk")
                clearChat()
            }
        }
    }


    fun clearChat() {

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

        btn_yes.setOnClickListener {
            val jsonObject = JSONObject()
            jsonObject.put("userId", user2Id)
            jsonObject.put("chatId", chatId.toInt())
            SocketManager.socket?.sendDataToServer(SocketManager.CLEAR_CHAT, jsonObject)
            dialogSuccessful.dismiss()
        }

        btn_no.setOnClickListener { dialogSuccessful.dismiss() }

        dialogSuccessful.show()
    }


    override fun onSocketCall(event: String?, vararg args: Any?) {

        if (event != null) {
            Log.e("onSocketCall", event)
        }
        when (event) {
            SocketManager.GET_CHAT -> {
                val mObject = args[0] as JSONObject
                val list = mObject.get("body") as JSONArray
                Log.i("====", list.toString())
                if (list.length() > 0) {
                    chatList.clear()
                    for (i in 0 until list.length()) {
                        val jsonobj = list.getJSONObject(i)
                        val gson = GsonBuilder().create()
                        val data = gson.fromJson(jsonobj.toString(), MessageList::class.java)
                        chatList.add(data)

                    }
                    chatList.reverse()

                    chatAdapter?.notifyDataSetChanged()
                    rvChat.smoothScrollToPosition(chatList.size-1)

                } else {
                    rvChat.visibility = View.GONE
                    //  tvNoMsgList.visibility = View.VISIBLE
                }
            }

            SocketManager.SEND_MESSAGE -> {
                rvChat.visibility = View.VISIBLE
                Log.i("====", "Send Message success")
                val mObject = args[0] as JSONObject
                val list = mObject.get("body") as JSONObject
                Log.i("=====", list.toString())
                val gson = GsonBuilder().create()
                val model = gson.fromJson(list.toString(), MessageList::class.java)
                chatList.add(model)

                chatAdapter?.notifyDataSetChanged()
                rvChat.scrollToPosition(chatList.size - 1)

            }

            SocketManager.CLEAR_CHAT -> {
                Log.i("====", "clear chat success")
                chatList.clear()
                chatAdapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onSocketConnect(vararg args: Any?) {

    }

    override fun onSocketDisconnect(vararg args: Any?) {

    }

    override fun onError(event: String?, vararg args: Any?) {

    }

    override fun onStart() {
        super.onStart()
        MyApplication.getSocketManager().onRegister(this)
    }

    override fun onStop() {
        super.onStop()
        MyApplication.getSocketManager().unRegister(this)
    }

}