package com.example.WMS.custom_Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.WMS.R
import kotlinx.android.synthetic.main.find_company_join.*

class Find_Company_Join( context: Context) : Dialog(context, R.style.CustomDialog) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var view= View.inflate(context,R.layout.find_company_join,null)
        setContentView(view)
        initView(view)
        setCanceledOnTouchOutside(true)
//        window!!.setGravity(Gravity.BOTTOM)
//        window!!.setLayout(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
    }
    fun initView(view: View){
        company_list.layoutManager= LinearLayoutManager(context)
        val mList1: List<String> = listOf("1", "3", "4", "5")
        var joinCompanyAdapter=Join_Company_Adapter(mList1)
        company_list.adapter=joinCompanyAdapter
        close.setOnClickListener {
            dismiss()
        }
    }
}