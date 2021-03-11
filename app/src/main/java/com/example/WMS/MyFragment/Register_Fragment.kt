package com.example.WMS.MyFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.WMS.MainActivity
import com.example.WMS.R
import com.xuexiang.xui.widget.toast.XToast
import kotlinx.android.synthetic.main.register.*

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
                (activity as MainActivity).supportFragmentManager.popBackStack()
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
}