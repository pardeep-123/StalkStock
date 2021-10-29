package com.live.stalkstockcommercial.ui.view.fragments.messages

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import com.live.stalkstockcommercial.OpenActivity
import com.stalkstock.commercial.view.model.ModelPojo
import com.live.stalkstockcommercial.ui.view.activities.Chat
import com.live.stalkstockcommercial.ui.view.adapters.messages.MessagesListAdapter
import com.stalkstock.R
import kotlinx.android.synthetic.main.fragment_messages.*
import kotlinx.android.synthetic.main.fragment_my_orders.*

class MessagesFragment : Fragment(), View.OnClickListener, MessagesListAdapter.OnMessageRecyclerViewListAdapter {
  //  var listner: CommunicationListner?=null
    var list:ArrayList<ModelPojo.MessageListModel>?=null
    var handler: Handler?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
       // listner!!.getYourFragmentActive(3)
//        handler= Handler(Looper.myLooper()!!)
//        handler!!.postDelayed({
            createMessageList()
//        },60)
    }
    private fun init(){

    }

    private  fun createMessageList(){
        if(list!=null){
            list!!.clear()
        }else{
            list= ArrayList()
        }
        list!!.add(ModelPojo.MessageListModel(R.drawable.chat_img_1,"Jamie Jai","Lorem ipsum dolor sit amet","2:30 PM",2))
        list!!.add(ModelPojo.MessageListModel(R.drawable.chat_img_2,"Jamie Jai","Lorem ipsum dolor sit amet","2:30 PM",0))
//        list!!.add(ModelPojo.MessageListModel(R.drawable.chat_img_3,"Jamie Jai","Lorem ipsum dolor sit amet","2:30 PM",0))
//        list!!.add(ModelPojo.MessageListModel(R.drawable.chat_img_1,"Jamie Jai","Lorem ipsum dolor sit amet","2:30 PM",0))
//        list!!.add(ModelPojo.MessageListModel(R.drawable.chat_img_2,"Jamie Jai","Lorem ipsum dolor sit amet","2:30 PM",0))
//        list!!.add(ModelPojo.MessageListModel(R.drawable.chat_img_3,"Jamie Jai","Lorem ipsum dolor sit amet","2:30 PM",0))
//        list!!.add(ModelPojo.MessageListModel(R.drawable.chat_img_1,"Jamie Jai","Lorem ipsum dolor sit amet","2:30 PM",0))
//        list!!.add(ModelPojo.MessageListModel(R.drawable.chat_img_2,"Jamie Jai","Lorem ipsum dolor sit amet","2:30 PM",0))
        rv_messagesList.adapter= MessagesListAdapter(requireActivity(), list!!, this@MessagesFragment)
        var dividerBetweenRecyclerViewItems = DividerItemDecoration(requireActivity(),
            DividerItemDecoration.VERTICAL)
        rv_messagesList.addItemDecoration(dividerBetweenRecyclerViewItems)

    }

    override fun onClick(p0: View?) {
        when(p0!!.id){

        }
    }

    override fun onMessgaeListItemClickListner(
        list: ArrayList<ModelPojo.MessageListModel>,
        position: Int
    ) {
        requireContext().OpenActivity(Chat::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        if(context is CommunicationListner){
//            listner= context as CommunicationListner
//        }else{
//            throw RuntimeException("Message Frag not Attached")
//        }
    }

    override fun onDetach() {
        super.onDetach()
       // listner= null
    }

}