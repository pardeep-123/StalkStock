package com.live.stalkstockcommercial.ui.view.activities

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
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.*
import kotlin.collections.ArrayList

class Chat : AppCompatActivity(), View.OnClickListener {

    var click = ""
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage=""
    var chatList : ArrayList<ChatData> = ArrayList()
    lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        init()

        setAdapter()

    }

    private fun setAdapter() {
        chatList.add(ChatData("What's Up?","17:00 PM","a","other"))
        chatList.add(ChatData("Lorem ipsum is a simply dummy text of the printing and typeset","17:00 PM","a","other"))
        chatList.add(ChatData("What's Up?","17:00 PM","b","me"))
        chatList.add(ChatData("Lorem ipsum is a simply dummy text of the printing and typeset","17:00 PM","b","me"))
        chatList.add(ChatData("Hello","11:58 PM","a","other"))
        chatList.add(ChatData("Hiii","11:59 PM","b","me"))


        chatAdapter = ChatAdapter(chatList)
        rvChat.adapter = chatAdapter
    }

    private fun selectImage(ivProduct: ImageView, type:String) {
        Album.image(this)
            .singleChoice()
            .camera(true)
            .columnCount(4)
            //.selectCount(1)
            //.checkedList(mAlbumFiles)
            .widget(
                Widget.newDarkBuilder(this)
                    .title(getString(R.string.app_name))
                    .build()
            )
            .onResult { result ->
                mAlbumFiles.addAll(result)
              //  Glide.with(this).load(result[0].path).into(ivProduct)
                if (type.equals("1"))
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
                if(click.equals("")){
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
                    chatList.add(ChatData(etMessage.text.toString(),"17:00 PM","a","me"))

                    chatAdapter.notifyItemInserted(chatList.size-1)
                    rvChat.smoothScrollToPosition(chatList.size-1)
                    etMessage.text.clear()
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


}