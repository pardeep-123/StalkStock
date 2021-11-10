package com.stalkstock.commercial.view.fragments.home.message

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.stalkstockcommercial.OpenActivity
import com.stalkstock.commercial.view.model.ModelPojo
import com.stalkstock.commercial.view.activities.Chat
import com.live.stalkstockcommercial.ui.view.adapters.messages.MessagesListAdapter
import com.stalkstock.R
import com.stalkstock.vender.Model.MessageList
import com.stalkstock.vender.adapter.MessageAdapter
import kotlinx.android.synthetic.main.fragment_messages.*
import kotlinx.android.synthetic.main.fragment_my_orders.*

class MessagesFragment : Fragment(), View.OnClickListener{
  //  var listner: CommunicationListner?=null
  var mcontext: Context? = null
    var list= ArrayList<MessageList>()
    var handler: Handler?=null
    private var messageAdapter: MessageAdapter? = null

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

        setAdapter()
    }

    private fun setAdapter() {
        rv_messagesList.layoutManager = LinearLayoutManager(mcontext)
        messageAdapter = MessageAdapter(mcontext!!, list)
        rv_messagesList.adapter = messageAdapter
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){

        }
    }

}