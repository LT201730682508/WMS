package com.example.WMS.MyFragment.Message_Notify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
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
    lateinit var empty_rl:RelativeLayout
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
        empty_rl=view.findViewById(R.id.empty_rl)
       initdata()
    }
   fun initdata(){
       message_recycler.layoutManager=LinearLayoutManager(requireContext())
       Message_Notify_Model.get_Invite(object :Message_Notify_Model.after_Show{
           override fun show(list: Array<Message_Notify_Model.invite_item>) {
               if(list.size==0){
                   empty_rl.visibility=View.VISIBLE
                   message_recycler.visibility=View.GONE
               }
               messageAdapter= Message_Adapter(list,activity as MainActivity)
               message_recycler.adapter=messageAdapter
           }
       },(activity as MainActivity).fragment_Manager.userinfo)

   }
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (isHidden) {
        } else {
             initdata()
        }
    }
}