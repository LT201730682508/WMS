package com.example.WMS.custom_Dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.View
import com.example.WMS.Open_Album
import com.example.WMS.R
import com.xuexiang.xui.XUI
import kotlinx.android.synthetic.main.create_company.*
import kotlinx.android.synthetic.main.take_albun_dialog.*

class Create_Company_Dialog( context: Context ) : Dialog(context,R.style.CustomDialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var view= View.inflate(context, R.layout.create_company,null)
        setContentView(view)
        initView()
    }
    fun initView(){
        make_sure.setOnClickListener {
            dismiss()
        }
    }

}