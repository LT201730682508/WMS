package com.example.WMS.custom_Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.WMS.R
import kotlinx.android.synthetic.main.alart_warning_dialog.*
import kotlinx.android.synthetic.main.find_company_join.*

class Alart_Warning_Dialog( context: Context,var showSure: Show_Sure,var str:String) : Dialog(context, R.style.CustomDialog) {
    lateinit var dialog_message:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var view = View.inflate(context, R.layout.alart_warning_dialog, null)
        setContentView(view)
        initView(view,showSure)
        setCanceledOnTouchOutside(true)

//        window!!.setGravity(Gravity.BOTTOM)
//        window!!.setLayout(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
    }

    fun initView(view: View,showSure: Show_Sure) {

        dialog_message=view.findViewById(R.id.dialog_message)
        dialog_message.text=str
        cancel.setOnClickListener {
            dismiss()
        }
        sure.setOnClickListener {
            dismiss()
            showSure.sure()
        }
    }
    interface Show_Sure{
        fun sure()
    }
}