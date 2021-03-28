package com.example.WMS.MyFragment.Warehouse.Create_Warehouse

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
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
import com.example.WMS.custom_Dialog.Alart_Warning_Dialog
import com.example.WMS.custom_Dialog.Create_Company_Dialog
import com.example.WMS.custom_Dialog.take_Album_Dialog
import com.xuexiang.xui.XUI
import com.xuexiang.xui.widget.button.roundbutton.RoundButton
import com.xuexiang.xui.widget.edittext.ClearEditText
import com.xuexiang.xui.widget.edittext.MultiLineEditText
import com.xuexiang.xui.widget.toast.XToast
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream


class Create_Warehouse_Fragment:Fragment() {
    lateinit var set_img:ImageView
    lateinit var ware_introduction: MultiLineEditText
    lateinit var set_name: ClearEditText
    lateinit var make_sure: RoundButton
    lateinit var set_address:ClearEditText
    lateinit var topbar:Base_Topbar
    lateinit var dialog:take_Album_Dialog
    lateinit var bitmap:Bitmap
    var hasImg:Boolean=false
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
             if(!this::dialog.isInitialized){
                 dialog= take_Album_Dialog(requireContext())
             }
             dialog.show()
         }
         make_sure.setOnClickListener {
             if (ware_introduction.isEmpty||set_name.text.toString().isEmpty()||set_address.text.toString().isEmpty()||!hasImg){
                 XToast.warning(requireContext(), "请完善信息").show()
             }else{
                 if((activity as MainActivity).fragment_Manager.userinfo.userInfo.companyId!=0){
                     var map=HashMap<String,String>()
                     map.put("warehouseName",set_name.text.toString())
                     map.put("warehouseAddress",set_address.text.toString())
                     map.put("warehouseDescription",ware_introduction.contentText)
                     map.put("companyId",(activity as MainActivity).fragment_Manager.userinfo.userInfo.companyId.toString())
                     Create_Warehouse_Model.getData(map,saveBitmapFile((set_img.getDrawable() as BitmapDrawable).getBitmap(),"warehouseImg"),"warehouseImg",object:Create_Warehouse_Model.Show{
                             override fun show(str: String) {
                                 XToast.info(requireContext(),str).show()
                                 if(str=="创建仓库成功"){
                                     (activity as MainActivity).fragment_Manager.pop()
                                 }
                             }
                         },(activity as MainActivity).fragment_Manager.userinfo)
                     }
                 else{

                     var alartWarningDialog= Alart_Warning_Dialog(requireContext(),object :Alart_Warning_Dialog.Show_Sure{
                         override fun sure() {
                             var createCompanyDialog= Create_Company_Dialog(requireContext(),(activity as MainActivity).fragment_Manager.userinfo.token,object:Create_Company_Dialog.change_Info{
                                 override fun change(createResult: Create_Company_Dialog.create_result) {
                                     (activity as MainActivity).fragment_Manager.userinfo.userInfo.companyId=createResult.companyId
                                     (activity as MainActivity).fragment_Manager.userinfo.userInfo.companyName=createResult.companyName
                                 }

                             })
                             createCompanyDialog.show()
                         }
                     },"您还没有加入任何企业，是否自行创建？")
                     alartWarningDialog.show()
                 }

             }
         }
    }

   fun saveBitmapFile(bitmap:Bitmap?,str:String):File{
           var file = File(requireContext().getFilesDir().getPath().toString()+str+".jpg");//将要保存图片的路径
           if(!file.exists()){
               try {
                   file.createNewFile();
               } catch ( e:Exception) {
                   print("@@@@@"+e)
               }
           }
           try {
               var bos = BufferedOutputStream(FileOutputStream(file))
               bitmap?.compress(Bitmap.CompressFormat.JPEG, 20, bos)
               bos.flush()
               bos.close()
           } catch (e: Exception) {
               print("@@@@@"+e)
           }
           return file
   }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        set_img.layoutParams.height=500
        set_img.layoutParams.width=500
        hasImg=true
        when (requestCode) {
            Open_Album.TAKE_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                bitmap =
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