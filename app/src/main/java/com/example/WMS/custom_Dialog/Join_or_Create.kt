package com.example.WMS.custom_Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.WMS.MainActivity
import com.example.WMS.R
import kotlinx.android.synthetic.main.find_company_join.*
import kotlinx.android.synthetic.main.join_or_create.*

class Join_or_Create (context: Context ,val token:String,val changeInfo: Create_Company_Dialog.change_Info) : Dialog(context, R.style.CustomDialog) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var view = View.inflate(context, R.layout.join_or_create, null)
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
        create_company.setOnClickListener {
            dismiss()
            var createCompanyDialog=Create_Company_Dialog(context ,token,changeInfo)
            createCompanyDialog.show()
        }
        join_company.setOnClickListener {
            dismiss()
        }
    }
}
