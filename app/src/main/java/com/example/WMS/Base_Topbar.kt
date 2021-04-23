package com.example.WMS

import android.view.View
import android.widget.*
import com.xuexiang.xui.XUI
import com.xuexiang.xui.widget.edittext.ClearEditText

class Base_Topbar(var view:View,var activity: MainActivity) {
    var back:ImageView
    var search_back:ImageView
    var search:ImageView
    var search_lin:LinearLayout
    var search_detail:LinearLayout
    var search_detail_back:ImageView
    var detail_make_sure:ImageView
    var detail_name:ClearEditText
    var radio_topbar:RadioGroup
    var base:LinearLayout
    var search_content:ClearEditText
    var more:ImageView
    var title:TextView
    var make_sure:ImageView
    var cancle:ImageView
    init {
        XUI.initTheme(activity)
        back=view.findViewById(R.id.back)
        search_back=view.findViewById(R.id.search_back)
        search=view.findViewById(R.id.search)
        search_lin=view.findViewById(R.id.search_base)
        search_detail=view.findViewById(R.id.search_detail)
        search_detail_back=view.findViewById(R.id.search_detail_back)
        detail_make_sure=view.findViewById(R.id.detail_make_sure)
        detail_name=view.findViewById(R.id.detail_name)
        radio_topbar=view.findViewById(R.id.radio_topbar)
        base=view.findViewById(R.id.base)
        search_content=view.findViewById(R.id.search_content)
        more=view.findViewById(R.id.more)
        title=view.findViewById(R.id.my_title)
        cancle=view.findViewById(R.id.search_back)
        make_sure=view.findViewById(R.id.make_sure)
        back.setOnClickListener {
            activity.fragment_Manager.pop()
        }
    }
    constructor(view:View,activity: MainActivity,ishow:Boolean):this(view,activity){
         if(ishow){
             search.visibility=View.VISIBLE
         }
        search.setOnClickListener {
            search_lin.visibility=View.VISIBLE
        }
        search_back.setOnClickListener {
            search_content.setText("");
            search_lin.visibility=View.GONE
        }
    }
    fun setTitle(str:String){
        title.text=str
    }
}