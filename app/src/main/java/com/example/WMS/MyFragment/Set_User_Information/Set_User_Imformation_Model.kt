package com.example.WMS.MyFragment.Set_User_Information

import com.example.WMS.BaseCallback
import com.example.WMS.OkHttpHelper
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.File
import java.io.IOException
import java.lang.Exception

class Set_User_Imformation_Model {
    companion object{
        fun modify_user_Img(token:String, file: File?, show: show){

            var okHttpHelper= OkHttpHelper.getInstance()
            okHttpHelper.post_for_form("http://121.199.22.134:8003/api-authentication/setUserImg?userToken="+token,null,file,"userImg",object :
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


                override fun onError(response: Response?, code: Int, e: Exception?) {
                    println("@@@@@4"+code+e)
                }

                override fun onSuccess(response: Response?, t: String?) {
                    show.show(t!!)
                }
            })
        }
    }
    interface show{
        fun show(str:String)
    }
}