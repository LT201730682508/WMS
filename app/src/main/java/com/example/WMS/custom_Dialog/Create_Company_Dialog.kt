package com.example.WMS.custom_Dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.WMS.*
import com.example.WMS.MyFragment.Home_Fragment
import com.example.WMS.MyFragment.Login_fragment
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import com.xuexiang.xui.XUI
import com.xuexiang.xui.widget.edittext.MultiLineEditText
import com.xuexiang.xui.widget.toast.XToast
import kotlinx.android.synthetic.main.create_company.*
import kotlinx.android.synthetic.main.take_albun_dialog.*
import java.io.IOException
import java.lang.Exception

class Create_Company_Dialog( context: Context ,val token:String,val changeInfo: change_Info) : Dialog(context,R.style.CustomDialog) {
        lateinit var company_name:EditText
    lateinit var company_address:EditText
    lateinit var company_introduction: MultiLineEditText
    lateinit var company_contact:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var view= View.inflate(context, R.layout.create_company_new,null)
        setContentView(view)
        company_name=view.findViewById(R.id.company_name)
        company_address=view.findViewById(R.id.company_address)
        company_contact=view.findViewById(R.id.company_contact)
        company_introduction=view.findViewById(R.id.company_introduction)
        initView()
    }
    fun initView(){
        make_sure.setOnClickListener {
           Create(company_name.text.toString(),company_address.text.toString(),company_introduction.contentText.toString(),company_contact.text.toString(),token,changeInfo)
            dismiss()
        }
    }

    fun Create(str:String,company_address:String,company_introduction:String,company_contact:String,token: String,changeInfo: change_Info){
        var map=HashMap<String,String>()
        map.put("companyName",str)
        map.put("companyDescription",company_introduction)
        map.put("companyAddress",company_address)
        map.put("companyContact",company_contact)
        var okHttpHelper= OkHttpHelper.getInstance()
        okHttpHelper.post_for_form("http://121.199.22.134:8003/api-inventory/createCompany?userToken="+token,map,null,null,object :
            BaseCallback<create_result>(){
            override fun onFailure(request: Request?, e: IOException?) {
                println("@@@@@1"+e)
            }
            override fun onResponse(response: Response?) {
                println("@@@@@2"+response)
            }

            override fun onSuccess_List(resultStr: String?) {
                println("@@@@@3"+resultStr)
            }

            override fun onSuccess(response: Response?, t: create_result?) {
                println("@@@@@3"+t)
                XToast.success(context,"创建成功").show()
                changeInfo.change(t!!)

            }

            override fun onError(response: Response?, code: Int, e: Exception?) {
                println("@@@@@4"+code+e)
                XToast.warning(context,"该公司名字已经被占用").show()
            }

        })
    }
   data class create_result(val companyId: Int,val companyName:String)
    interface change_Info{
        fun change(createResult: create_result)
    }
}