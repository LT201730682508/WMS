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
import java.io.IOException
import java.lang.Exception

class Create_Area_Dialog (context: Context, val token:String, val warehouse_id: Int, var result: AddResult) : Dialog(context, R.style.CustomDialog){
    lateinit var dialog_title: TextView
    lateinit var area_name: EditText
    lateinit var make_sure: Button
    lateinit var area_num:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var view= View.inflate(context, R.layout.create_company,null)
        setContentView(view)
        initView(view)
    }
    fun initView(view: View){
        dialog_title=view.findViewById(R.id.dialog_title)
        dialog_title.text="新增区域"
        area_name=view.findViewById(R.id.company_name)
        area_name.hint="请输入区域名称"
        make_sure=view.findViewById(R.id.make_sure)
        area_num=view.findViewById(R.id.area_num)
        area_num.visibility=View.VISIBLE
        make_sure.setOnClickListener {
            if(!area_name.text.isEmpty()&&!area_num.text.isEmpty()){
                Create( token,area_name.text.toString(),warehouse_id,area_num.text.toString().toInt(),result)
            }

            dismiss()
        }
    }

    fun Create(token: String ,name:String,warehouse_id: Int,num:Int,result: AddResult){
        var okHttpHelper= OkHttpHelper.getInstance()
        okHttpHelper.post_for_object("http://121.199.22.134:8003/api-inventory/addArea/"+name+"/"+warehouse_id+"/"+num+"?userToken="+token,null,object :
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
                if(t=="添加仓库区域成功")
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


    interface AddResult{
        fun result()
    }
}