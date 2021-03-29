package com.example.WMS.MyFragment.Set_User_Information

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.WMS.Base_Topbar
import com.example.WMS.MainActivity
import com.example.WMS.Open_Album
import com.example.WMS.R
import com.example.WMS.custom_Dialog.take_Album_Dialog
import com.xuexiang.xui.XUI
import com.xuexiang.xui.widget.button.roundbutton.RoundButton
import com.xuexiang.xui.widget.edittext.ClearEditText
import kotlinx.android.synthetic.main.set_user_information.*

class Set_User_Information_Fragment:Fragment() {
    lateinit var baseTopbar: Base_Topbar
    lateinit var user_img:ImageView
    lateinit var company_name:TextView
    lateinit var user_account:TextView
    lateinit var sure:RoundButton
    lateinit var dialog: take_Album_Dialog
    lateinit var exit:RoundButton
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.set_user_information,container,false)
        init(view)
        return view
    }
    fun init(view:View) {
        baseTopbar = Base_Topbar(view, activity as MainActivity, false)
        user_img=view.findViewById(R.id.user_img)
        company_name=view.findViewById(R.id.company_name)
        user_account=view.findViewById(R.id.user_account)
        sure=view.findViewById(R.id.sure)
        exit=view.findViewById(R.id.exit)

        user_account.text="账号："+(activity as MainActivity).fragment_Manager.userinfo.userInfo.userName
        company_name.setText((activity as MainActivity).fragment_Manager.userinfo.userInfo.companyName)

        user_img.setOnClickListener {
            if(this::dialog.isLateinit){
                dialog= take_Album_Dialog(requireContext())
            }
            dialog.show()
        }
        sure.setOnClickListener {
            (activity as MainActivity).fragment_Manager.pop()
        }

        exit.setOnClickListener {
            (activity as MainActivity).fragment_Manager.return_login()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Open_Album.TAKE_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                val bitmap =
                    BitmapFactory.decodeStream(requireActivity().contentResolver.openInputStream(
                        Open_Album.uri))
                Glide.with(this).load(bitmap).into(user_img)
            }
            Open_Album.CHOOSE_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                if (Build.VERSION.SDK_INT >= 19) Open_Album.handleImageOnKitKat(activity,data,user_img) else Open_Album.handleImageBeforeKitKat(activity,data,user_img)
            }
            else -> {
            }
        }
    }
}