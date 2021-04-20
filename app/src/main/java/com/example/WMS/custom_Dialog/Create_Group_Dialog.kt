package com.example.WMS.custom_Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.WMS.BaseCallback
import com.example.WMS.OkHttpHelper
import com.example.WMS.R
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import com.xuexiang.xui.widget.toast.XToast
import kotlinx.android.synthetic.main.create_company.*
import java.io.IOException
import java.lang.Exception

class Create_Group_Dialog (context: Context, val token:String,val warehouse_id: Int,var result: AddResult) : Dialog(context, R.style.CustomDialog){
    lateinit var dialog_title:TextView
    lateinit var group_name:EditText
    lateinit var make_sure:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var view= View.inflate(context, R.layout.create_company,null)
        setContentView(view)
        initView(view)
    }
    fun initView(view:View){
        dialog_title=view.findViewById(R.id.dialog_title)
        dialog_title.text="新增部门"
        group_name=view.findViewById(R.id.company_name)
        group_name.hint="请输入部门名称"
        make_sure=view.findViewById(R.id.make_sure)
        make_sure.setOnClickListener {
            if(!group_name.text.isEmpty()){
                var params=addGroupParams(warehouse_id,group_name.text.toString())
                Create( token,params,result)
            }

            dismiss()
        }
    }

    fun Create(token: String ,addGroupParams:addGroupParams,result:AddResult){
        var okHttpHelper= OkHttpHelper.getInstance()
        okHttpHelper.post_for_object("http://121.199.22.134:8003/api-authority/createGroup?userToken="+token,addGroupParams,object :
            BaseCallback<String>(){
            override fun onFailure(request: Request?, e: IOException?) {
                println("@@@@@1"+e)
            }
            override fun onResponse(response: Response?) {
                println("@@@@@2"+response)
            }

            override fun onSuccess_List(resultStr: String?) {
                println("@@@@@3"+resultStr)
            }

            override fun onSuccess(response: Response?, t: String?) {
                println("@@@@@3"+t)
                if(t=="OK")
                XToast.success(context,t!!).show()
                else XToast.warning(context,t!!).show()
                result.result()
            }

            override fun onError(response: Response?, code: Int, e: Exception?) {
                println("@@@@@4"+code+e)
                XToast.warning(context,"创建失败").show()
            }

        })
    }

    data class addGroupParams(val warehouse_id:Int,val group_name:String)
    interface AddResult{
        fun result()
    }
}