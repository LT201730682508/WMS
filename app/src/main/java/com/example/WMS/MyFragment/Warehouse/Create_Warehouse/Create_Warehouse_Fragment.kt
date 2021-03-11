package com.example.WMS.MyFragment.Warehouse.Create_Warehouse

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
import com.xuexiang.xui.widget.edittext.MultiLineEditText
import com.xuexiang.xui.widget.toast.XToast


class Create_Warehouse_Fragment:Fragment() {
    lateinit var set_img:ImageView
    lateinit var ware_introduction: MultiLineEditText
    lateinit var set_name: ClearEditText
    lateinit var make_sure: RoundButton
    lateinit var set_address:ClearEditText
    lateinit var topbar:Base_Topbar
    lateinit var dialog:take_Album_Dialog
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        XUI.initTheme(activity)
        var view=inflater.inflate(R.layout.create_warehouse,container,false)
        init(view)
        return view
    }
     fun init(view:View){
         topbar= Base_Topbar(view,activity as MainActivity,false)
         topbar.setTitle("仓库管理")
         set_img=view.findViewById(R.id.set_img)
         set_address=view.findViewById(R.id.set_warehouse_address)
         ware_introduction=view.findViewById(R.id.ware_introduction)
         set_name=view.findViewById(R.id.set_warehouse_name)
         make_sure=view.findViewById(R.id.ware_make_sure)



         set_img.setOnClickListener {
             if(this::dialog.isLateinit){
                 dialog= take_Album_Dialog(requireContext())
             }
             dialog.show()
         }
         make_sure.setOnClickListener {
             if (ware_introduction.isEmpty||set_name.text.toString().isEmpty()||set_address.text.toString().isEmpty()){
                 XToast.warning(requireContext(), "请完善信息").show()
             }else{
                 (activity as MainActivity).fragment_Manager.pop()
             }

         }

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        set_img.layoutParams.height=500
        set_img.layoutParams.width=500
        when (requestCode) {
            Open_Album.TAKE_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                val bitmap =
                    BitmapFactory.decodeStream(requireActivity().contentResolver.openInputStream(
                        Open_Album.uri))
                Glide.with(this).load(bitmap).into(set_img)
            }
            Open_Album.CHOOSE_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                if (Build.VERSION.SDK_INT >= 19) Open_Album.handleImageOnKitKat(activity,data,set_img) else Open_Album.handleImageBeforeKitKat(activity,data,set_img)
            }
            else -> {
            }
        }
    }
}