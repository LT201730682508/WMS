package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Add_Member

import com.example.WMS.BaseCallback
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Imformation.Member_Imformation_Model
import com.example.WMS.OkHttpHelper
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import java.io.IOException

class Add_Member_Model {


    companion object{
        fun search_user(memberParams: member_params) {
            val ok = OkHttpHelper.getInstance()
            ok.get_for_list(
                "http://121.199.22.134:8003/api-authority/getInviteUserInfo/"+memberParams.token+"/"+memberParams.userId,
                object : BaseCallback<String>() {
                    override fun onFailure(
                        request: Request,
                        e: IOException
                    ) {
                        println("failure$e")
                    }

                    override fun onResponse(response: Response) {
                        println("response$response")
                    }

                    override fun onSuccess_List(resultStr: String) {
                    }
                    override fun onError(
                        response: Response,
                        code: Int,
                        e: Exception
                    ) {
                        println("error$response$e")
                    }

                    override fun onSuccess(response: Response?, t: String?) {

                    }
                })
        }


        fun send_invite(memberInviteParams: member_invite_params) {
            val ok = OkHttpHelper.getInstance()
            ok.post_for_object(
                "http://121.199.22.134:8003/api-authority/postInvitation" ,memberInviteParams,
                object : BaseCallback<String>() {
                    override fun onFailure(
                        request: Request,
                        e: IOException
                    ) {
                        println("failure$e")
                    }

                    override fun onResponse(response: Response) {
                        println("response$response")
                    }

                    override fun onSuccess_List(resultStr: String) {
                    }
                    override fun onError(
                        response: Response,
                        code: Int,
                        e: Exception
                    ) {
                        println("error$response$e")
                    }

                    override fun onSuccess(response: Response?, t: String?) {

                    }
                })
        }

    }

    data class member_params(val token:String,val userId:Int)

    data class member_invite_params(val token:String,val userId:Int,val role:String)
}