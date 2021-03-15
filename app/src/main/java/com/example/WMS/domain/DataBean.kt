package com.example.WMS.domain

class DataBean {
    //outPrice改回去inPrice
    data class ProductIn(var productId:Int,var productName:String,var totalAmount:Int,var productDescription:String,
                         var inPrice:Int,var productCategory:String,var productCode:String,var productImg:String,var updateTime:String)

    data class ProductOut(var productId:Int,var productName:String,var totalAmount:Int,var productDescription:String,
                        var outPrice:Int,var productCategory:String,var productCode:String,var productImg:String,var updateTime:String)

    data class ProductIn_post(var productName:String,var productDescription:String,
                              var productCategory:String,var productCode:String,var productImg:String)

    data class Product(var productId: Int,var productName:String,var productDescription:String,
                              var productCategory:String,var productCode:String,var productImg:String)

    data class Supplier_add(var supplierName: String,var companyId: String)

    data class Receiver_add(var receiverName: String,var companyId: String)

    data class Member(var userId:Int,var userName:String,var role:String)

    data class Warehouse_Join(var warehouseId:Int,var warehouseName:String)

    data class Warehouse_All(var warehouseId:Int,var warehouseName:String)

    data class Supplier(var supplierId:Int,var supplierName:String)

    data class Receiver(var receiverId:Int,var receiverName:String)

    data class InWarehouseRecord(var inId:Int,var userName: String,var productId: Int,var productName: String,var productImg: String,
                                 var warehouseId: Int,var warehouseName: String,var amount:Int,var inPrice: Int,var supplierName: String,
                                 var note:String,var createTime:String)

    data class OutWarehouseRecord(var outId:Int,var userName: String,var productId: Int,var productName: String,var productImg: String,
                                  var warehouseId: Int,var warehouseName: String,var amount:Int,var outPrice: Int,var receiverName: String,
                                  var note:String,var createTime:String)

    data class AllStaff(var userId: Int,var userName: String,var role: String)

    data class AllRole(var role: String,var authorities:String)

    data class InviteList(var InvitationId:Int,var warehouseId: Int,var warehouseName: String,var inviter:String,var role: String)
}
