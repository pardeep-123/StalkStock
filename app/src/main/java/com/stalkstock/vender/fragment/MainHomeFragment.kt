package com.stalkstock.vender.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.vender.adapter.TestAdapter
import com.stalkstock.vender.ui.MessageActivity
import com.stalkstock.vender.ui.NotificationScreen
import com.stalkstock.vender.ui.SearchScreen
import com.stalkstock.vender.ui.SelectCategory
import stalkstockcommercial.ui.view.activities.FilterActivity

class MainHomeFragment : Fragment(), View.OnClickListener {
    var mcontext: Context? = null
    private var testAdapter: TestAdapter? = null
    lateinit var recyclerview: RecyclerView
    var clickMsg = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_home_two_fragment, container, false)
        mcontext = activity
        recyclerview = view.findViewById(R.id.recyclerview)
        testAdapter = TestAdapter(mcontext)
        recyclerview.setLayoutManager(LinearLayoutManager(mcontext))
        recyclerview.setAdapter(testAdapter)
        val imageView = view.findViewById<ImageView>(R.id.notification)
        val imageView1 = view.findViewById<ImageView>(R.id.filter)
        val iv_msg = view.findViewById<ImageView>(R.id.iv_msg)
        val editText = view.findViewById<RelativeLayout>(R.id.edit_search)
        val button = view.findViewById<Button>(R.id.addproductbutton)
        button.setOnClickListener(this)
        imageView.setOnClickListener(this)
        imageView1.setOnClickListener(this)
        editText.setOnClickListener(this)
        iv_msg.setOnClickListener {
            if (clickMsg == 0) {
                clickMsg = 1
                val intent = Intent(requireActivity(), MessageActivity::class.java)
                startActivity(intent)
            } else {
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        clickMsg = 0
    }

    override fun onClick(view: View) {
        val id = view.id
        when (id) {
            R.id.notification -> {
                val intent = Intent(activity, NotificationScreen::class.java)
                startActivity(intent)
            }
            R.id.filter -> {
                val intent2 = Intent(activity, FilterActivity::class.java)
                startActivity(intent2)
            }
            R.id.edit_search -> {
                val intent1 = Intent(activity, SearchScreen::class.java)
                startActivity(intent1)
            }
            R.id.addproductbutton -> {
                val i = Intent(activity, SelectCategory::class.java)
                startActivity(i)
            }
        }
    }
}