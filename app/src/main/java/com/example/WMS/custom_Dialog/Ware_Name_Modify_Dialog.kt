package com.example.WMS.custom_Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.WMS.R
import kotlinx.android.synthetic.main.create_company.*

class Ware_Name_Modify_Dialog ( context: Context) : Dialog(context, R.style.CustomDialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var view = View.inflate(context, R.layout.modify_warehouse_name, null)
        setContentView(view)
        initView()
    }

    fun initView() {
        make_sure.setOnClickListener {
            dismiss()
        }
    }
}