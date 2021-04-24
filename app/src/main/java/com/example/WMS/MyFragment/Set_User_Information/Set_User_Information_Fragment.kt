package com.example.WMS.MyFragment.Set_User_Information

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.WMS.Base_Topbar
import com.example.WMS.MainActivity
import com.example.WMS.Open_Album
import com.example.WMS.R
import com.example.WMS.custom_Dialog.take_Album_Dialog
import com.xuexiang.xui.widget.button.roundbutton.RoundButton
import com.xuexiang.xui.widget.toast.XToast
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

class Set_User_Information_Fragment:Fragment() {
    lateinit var baseTopbar: Base_Topbar
    lateinit var user_img:ImageView
    lateinit var company_name:TextView
    lateinit var user_account:TextView
    lateinit var company_address:TextView
    lateinit var company_contact:TextView
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
    fun setImg(Str:String){
        Glide.with(this ).load(Str).into(user_img)
    }
    fun init(view:View) {
        baseTopbar = Base_Topbar(view, activity as MainActivity, false)
        user_img=view.findViewById(R.id.user_img)
        company_name=view.findViewById(R.id.company_name)
        company_contact=view.findViewById(R.id.company_contact)
        user_account=view.findViewById(R.id.user_account)
        company_address=view.findViewById(R.id.company_address)
        sure=view.findViewById(R.id.sure)
        exit=view.findViewById(R.id.exit)
        if((activity as MainActivity).fragment_Manager.userinfo.userInfo.userImg!=null){
            setImg((activity as MainActivity).fragment_Manager.userinfo.userInfo.userImg)
        }

        user_account.text="账号："+(activity as MainActivity).fragment_Manager.userinfo.userInfo.userName
        company_name.setText((activity as MainActivity).fragment_Manager.userinfo.userInfo.companyName)
        company_address.text="公司地址："+(activity as MainActivity).fragment_Manager.userinfo.userInfo.companyAddress

        company_contact.text="公司联系方式："+(activity as MainActivity).fragment_Manager.userinfo.userInfo.companyContact
        user_img.setOnClickListener {
            if(this::dialog.isLateinit){
                dialog= take_Album_Dialog(requireContext())
            }
            dialog.show()
        }
        sure.setOnClickListener {

            Set_User_Imformation_Model.modify_user_Img((activity as MainActivity).fragment_Manager.userinfo.token,saveBitmapFile((user_img.getDrawable()),"userImg"),object :Set_User_Imformation_Model.show{
                override fun show(str: String) {
                    (activity as MainActivity).fragment_Manager.userinfo.userInfo.userImg=str
                    XToast.success(requireContext(),"更改成功").show()
                    (activity as MainActivity).fragment_Manager.pop()
                }

            })

        }

        exit.setOnClickListener {
            (activity as MainActivity).fragment_Manager.return_login()
        }
    }
    fun saveBitmapFile(drawable: Drawable, img: String): File? {
        val file = File(
            requireContext().filesDir.path.toString() + img + ".jpg"
        ) //将要保存图片的路径
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: Exception) {
            }
        }
        try {
            val bos =
                BufferedOutputStream(FileOutputStream(file))
            var bitmap= drawable.toBitmap()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, bos)
            bos.flush()
            bos.close()
        } catch (e: Exception) {
        }
        return file
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
                setImg(Open_Album.uri.toString())
            }
            Open_Album.CHOOSE_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                if (Build.VERSION.SDK_INT >= 19) Open_Album.handleImageOnKitKat(activity,data,user_img) else Open_Album.handleImageBeforeKitKat(activity,data,user_img)
            }
            else -> {
            }
        }
    }
}