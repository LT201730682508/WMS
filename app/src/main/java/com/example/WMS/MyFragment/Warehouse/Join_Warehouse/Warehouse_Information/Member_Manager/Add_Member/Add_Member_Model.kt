package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Add_Member

import com.example.WMS.BaseCallback
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Imformation.Member_Imformation_Model
import com.example.WMS.OkHttpHelper
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import com.xuexiang.xui.widget.toast.XToast
import java.io.IOException

class Add_Member_Model {


    companion object{
        fun search_user(show:show,memberParams: member_params) {
            val ok = OkHttpHelper.getInstance()
            ok.get_for_object(
                "http://121.199.22.134:8003/api-authority/searchUser?userToken=" +memberParams.token+"&userName="+memberParams.userName,
                object : BaseCallback<search_member_imformation>() {
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

                    override fun onSuccess(response: Response?, t: search_member_imformation?) {
                        show.shou(t!!)
                    }
                })
        }


        fun send_invite(token: String,memberInviteParams: member_invite_params,inviteShow: invite_show) {
            val ok = OkHttpHelper.getInstance()
            ok.post_for_object(
                "http://121.199.22.134:8003/api-authority/postInvitation?userToken="+token ,memberInviteParams,
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
                        inviteShow.shou(t!!)
                    }
                })
        }
        interface show{
            fun shou(str:search_member_imformation)
        }
        interface invite_show{
            fun shou(str:String)
        }
    }

    data class member_params(val token:String,val userName:String)
    data class search_member_imformation(val user_name:String,val user_img:String)
    data class member_invite_params(val warehouse_id:Int,val invitee_name:String,val role:String)
}