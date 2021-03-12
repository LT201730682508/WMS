package com.example.WMS.custom_Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.WMS.R
import kotlinx.android.synthetic.main.alart_warning_dialog.*
import kotlinx.android.synthetic.main.find_company_join.*

class Alart_Warning_Dialog( context: Context) : Dialog(context, R.style.CustomDialog) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var view = View.inflate(context, R.layout.alart_warning_dialog, null)
        setContentView(view)
        initView(view)
        setCanceledOnTouchOutside(true)
//        window!!.setGravity(Gravity.BOTTOM)
//        window!!.setLayout(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
    }

    fun initView(view: View) {
        cancel.setOnClickListener {
            dismiss()
        }
        sure.setOnClickListener {
            dismiss()
            var createCompanyDialog=Create_Company_Dialog(context)
            createCompanyDialog.show()
        }
    }
}