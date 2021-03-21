package com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.WMS.Base_Topbar
import com.example.WMS.MainActivity
import com.example.WMS.MyFragment.Warehouse.All_Warehouse.All_Warehouse_Model
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Member_Manager.Member_Manager_Fragment
import com.example.WMS.MyFragment.Warehouse.Join_Warehouse.Warehouse_Information.Warehouse_Detail_Information.Warehouse_Detail_Information
import com.example.WMS.R
import com.example.WMS.WarehouseIn.WarehouseInList_Fragment
import com.example.WMS.WarehouseOut.WarehouseOutList_Fragment
import com.example.WMS.custom_Dialog.Alart_Warning_Dialog
import com.example.WMS.custom_Dialog.Ware_Name_Modify_Dialog
import com.xuexiang.xui.widget.toast.XToast

class Warehouse_Information (var item: All_Warehouse_Model.Warehouse):Fragment(){
    lateinit var ware_information: TextView
    lateinit var ware_in: TextView
    lateinit var ware_out: TextView
    lateinit var ware_menber: TextView
    lateinit var ware_address:TextView
    lateinit var ware_number:TextView
    lateinit var warehouse_name:TextView
    lateinit var base_Top_Bar: Base_Topbar
    lateinit var ware_name_modification:TextView
    lateinit var ware_delete:TextView
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
        ware_number=view.findViewById(R.id.warehouse_number)
        ware_information=view.findViewById(R.id.information)
        ware_in=view.findViewById(R.id.ware_in)
        ware_out=view.findViewById(R.id.ware_out)
        ware_menber=view.findViewById(R.id.ware_menber)
        ware_name_modification=view.findViewById(R.id.ware_name_modification)
        ware_delete=view.findViewById(R.id.ware_delete)


        warehouse_name.text=item.warehouseName
        ware_information.setOnClickListener {
            var warehouseDetailInformation=Warehouse_Detail_Information()
            (activity as MainActivity).fragment_Manager.hide_all(warehouseDetailInformation)
        }
        ware_in.setOnClickListener {
            var warehouseinlistFragment=WarehouseInList_Fragment()
            (activity as MainActivity).fragment_Manager.hide_all(warehouseinlistFragment)
        }
        ware_out.setOnClickListener {
            var warehouseoutlistFragment=WarehouseOutList_Fragment()
            (activity as MainActivity).fragment_Manager.hide_all(warehouseoutlistFragment)
        }
        ware_menber.setOnClickListener {
            var memberManagerFragment=Member_Manager_Fragment()
            (activity as MainActivity).fragment_Manager.hide_all(memberManagerFragment)
        }
        ware_name_modification.setOnClickListener {
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
        ware_delete.setOnClickListener {
            var alartWarningDialog= Alart_Warning_Dialog(requireContext(),object :Alart_Warning_Dialog.Show_Sure{
                override fun sure() {
                      var a="Aa"
                      Warehouse_Imformation_Model.getData_Delete(a,object :Warehouse_Imformation_Model.Warehouse_imfor_Show{
                          override fun show(str: String) {
                               XToast.info(requireContext(),str).show()
                          }

                      },(activity as MainActivity).fragment_Manager.userinfo)
                }
            },"您是否确定删除该仓库？")

            alartWarningDialog.show()
        }
    }
}