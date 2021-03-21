package com.example.WMS.MyFragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.WMS.BaseCallback
import com.example.WMS.MainActivity
import com.example.WMS.OkHttpHelper
import com.example.WMS.R
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import com.xuexiang.xui.widget.toast.XToast
import kotlinx.android.synthetic.main.register.*
import java.io.IOException
import java.lang.Exception

class Register_Fragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.register,container,false)
        var signUn_goBackButton=view.findViewById<Button>(R.id.signUn_goBackButton)
        var signUp_account=view.findViewById<EditText>(R.id.signUp_account)
        var signUp_password=view.findViewById<EditText>(R.id.signUp_password)
        var signUp_signUpButton=view.findViewById<Button>(R.id.signUp_signUpButton)
        signUn_goBackButton.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack()
    }
        signUp_signUpButton.setOnClickListener {
            if (check_null()){
                register(signUp_account.text.toString(),signUp_password.text.toString())
            }else{
                XToast.warning(requireContext(),"请完善需要的账号密码").show()
            }
        }
        return view
    }

    fun check_null():Boolean{
        if (signUp_account.text.isEmpty()||signUp_password.text.isEmpty()){
            return false
        }
        return true
    }
    fun register(username:String,password:String ){
        var map=HashMap<String,String>()
        map.put("userName",username)
        map.put("password",password)
        var okHttpHelper= OkHttpHelper.getInstance()
        okHttpHelper.post_for_object("http://121.199.22.134:8003/api-authentication/register",map,object :
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
                if(t=="注册成功"){
                  XToast.success(requireContext(),t).show()
                    (activity as MainActivity).supportFragmentManager.popBackStack()
                }
            }

            override fun onError(response: Response?, code: Int, e: Exception?) {
                println("@@@@@4"+code+e)
                var handler= Handler(Looper.getMainLooper())
                handler.post{
                    XToast.error(requireContext(),response!!.body().toString()).show()
                }

            }

        })
    }


}