package com.example.WMS.MyFragment.Message_Notify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.WMS.Base_Topbar
import com.example.WMS.MainActivity
import com.example.WMS.R

class Message_Notify_Fragment:Fragment() {
    lateinit var baseTopbar:Base_Topbar
    lateinit var messageAdapter:Message_Adapter
    lateinit var message_recycler:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.message_notify,container,false)
        init(view)
        return view
    }
    fun init(view:View){
        baseTopbar= Base_Topbar(view,activity as MainActivity,true)
        message_recycler=view.findViewById(R.id.message_list)
        message_recycler.layoutManager=LinearLayoutManager(requireContext())
        val mList1: List<String> = listOf("1", "3", "4", "5", "3","1", "3", "4", "5", "3","1", "3", "4", "5", "3","1", "3", "4", "5", "3","1", "3", "4", "5", "3")
        messageAdapter= Message_Adapter(mList1,activity as MainActivity)
        message_recycler.adapter=messageAdapter
    }
}