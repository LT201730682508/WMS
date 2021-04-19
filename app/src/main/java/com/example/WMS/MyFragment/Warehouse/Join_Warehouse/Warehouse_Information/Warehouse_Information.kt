package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.WMS.Base_Topbar
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Warehouse.All_Warehouse.All_Warehouse_Model
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Member_Manager_Fragment
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Warehouse_Detail_Information.Warehouse_Detail_Information
import com.example.WMS.MyFragment.Warehouse.Warehouse_Authority_List
import com.example.WMS.MyFragment.Warehouse.Warehouse_authority_Model
import com.example.WMS.R
import com.example.WMS.WareOperation.WarehouseIn.WarehouseInList_Fragment
import com.example.WMS.WareOperation.WarehouseOut.WarehouseOutList_Fragment
import com.example.WMS.custom_Dialog.Alart_Warning_Dialog
import com.example.WMS.custom_Dialog.Ware_Name_Modify_Dialog
import com.xuexiang.xui.widget.toast.XToast
import kotlinx.android.synthetic.main.warehouse_information.*

class Warehouse_Information(var item: All_Warehouse_Model.Warehouse):Fragment(){
    lateinit var ware_in: TextView
    lateinit var ware_out: TextView
    lateinit var ware_menber: TextView
    lateinit var ware_address:TextView
    lateinit var warehouse_des:TextView
    lateinit var warehouse_name:TextView
    lateinit var base_Top_Bar: Base_Topbar
    lateinit var ware_name_modification:TextView
    lateinit var ware_delete:TextView
    lateinit var warehouse_img:ImageView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.warehouse_information,container,false)
        init(view)
        return view
    }
    fun init(view:View){
        base_Top_Bar= Base_Topbar(view,activity as MainActivity,false)
        base_Top_Bar.setTitle("我加入的仓库")
        warehouse_name=view.findViewById(R.id.warehouse_name)
        ware_address=view.findViewById(R.id.warehouse_address)
        warehouse_des=view.findViewById(R.id.warehouse_des)

        warehouse_img=view.findViewById(R.id.warehouse_img)
        ware_in=view.findViewById(R.id.ware_in)
        ware_out=view.findViewById(R.id.ware_out)
        ware_menber=view.findViewById(R.id.ware_menber)
        ware_name_modification=view.findViewById(R.id.ware_name_modification)
        ware_delete=view.findViewById(R.id.ware_delete)
        Glide.with(this).load(item.warehouseImg).into(warehouse_img)
        warehouse_name.text=item.warehouseName
        ware_address.text=item.warehouseAddress
        warehouse_des.text=item.warehouseDescription

        Warehouse_authority_Model.getRole(item.warehouseId,(activity as MainActivity).fragment_Manager.userinfo.token,object :Warehouse_authority_Model.getRole{
            override fun get(authority: Warehouse_authority_Model.authority) {
                 Warehouse_Authority_List.authorityList_Map.put(item.warehouseId.toString()+(activity as MainActivity).fragment_Manager.userinfo.token,authority.authorities)
                Warehouse_Authority_List.roleList_Map.put(item.warehouseId.toString()+(activity as MainActivity).fragment_Manager.userinfo.token,authority.role)

                if (authority.role!="库主"||authority==null){
                    delete_modify_ll.visibility=View.GONE
                }
            }

        })

        ware_in.setOnClickListener {
            var warehouseinlistFragment=WarehouseInList_Fragment()
            (activity as MainActivity).fragment_Manager.hide_all(warehouseinlistFragment)
        }
        ware_out.setOnClickListener {
            var warehouseoutlistFragment=WarehouseOutList_Fragment()
            (activity as MainActivity).fragment_Manager.hide_all(warehouseoutlistFragment)
        }
        ware_menber.setOnClickListener {
            var memberManagerFragment=Member_Manager_Fragment(item.warehouseId)
            (activity as MainActivity).fragment_Manager.hide_all(memberManagerFragment)
        }
        ware_name_modification.setOnClickListener {
            val role=Warehouse_Authority_List.roleList_Map.get(item.warehouseId.toString()+(activity as MainActivity).fragment_Manager.userinfo.token)
            if(role=="库主"){
                var wareNameModifyDialog=Ware_Name_Modify_Dialog(requireContext(),object :Ware_Name_Modify_Dialog.Modify{
                    override fun modify(changName: String) {
                        var modify_Params= Warehouse_Imformation_Model.Modify_Params(item.warehouseId,changName)
                        Warehouse_Imformation_Model.getData_Modefy(modify_Params,object :Warehouse_Imformation_Model.Warehouse_imfor_Show{
                            override fun show(str: String) {
                                XToast.info(requireContext(),str).show()
                                if(str=="修改仓库名字成功"){
                                    warehouse_name.text=changName
                                }
                            }

                        },(activity as MainActivity).fragment_Manager.userinfo)
                    }
                })
                wareNameModifyDialog.show()
            }
          else{
                XToast.warning(requireContext(),"您没有相关权限").show()
            }
        }
        ware_delete.setOnClickListener {
            val role=Warehouse_Authority_List.roleList_Map.get(item.warehouseId.toString()+(activity as MainActivity).fragment_Manager.userinfo.token)
            if(role=="库主") {
                var alartWarningDialog =
                    Alart_Warning_Dialog(requireContext(), object : Alart_Warning_Dialog.Show_Sure {
                        override fun sure() {

                            Warehouse_Imformation_Model.getData_Delete(
                                item.warehouseId,
                                object : Warehouse_Imformation_Model.Warehouse_imfor_Show {
                                    override fun show(str: String) {
                                        (activity as MainActivity).fragment_Manager.pop()
                                        XToast.info(requireContext(), str).show()
                                    }

                                },
                                (activity as MainActivity).fragment_Manager.userinfo
                            )
                        }
                    }, "您是否确定删除该仓库？")

                alartWarningDialog.show()
            }else{
                XToast.warning(requireContext(),"您没有相关权限").show()
            }
        }
    }
}