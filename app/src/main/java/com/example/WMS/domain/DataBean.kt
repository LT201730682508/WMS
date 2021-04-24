package com.example.WMS.domain

class DataBean {
    //出入库按钮下的的databean
    data class ProductIn(var id:Int,var productId:Int,var productName:String,var totalAmount:Int,var productDescription:String,
                         var inPrice:Double,var productCategory:String,var productCode:String,var productImg:String,var updateTime:String, var productTags:String)

    data class ProductOut(var id:Int,var productId:Int,var productName:String,var totalAmount:Int,var productDescription:String,
                        var outPrice:Double,var productCategory:String,var productCode:String,var productImg:String,var updateTime:String, var productTags:String)

    data class ProductIn_post(var productId: Int ,var productName: String, var productDescription: String,
                              var productCategory: String, var productCode: String, var productImg: String, var productTags:String)

    data class ProductIn_inWarehouse(var id:Int,var supplierId: String,var inPrice: Double,var amount: Int,var note: String)

    data class ProductOut_outWarehouse(var id:Int,var receiverId: String,var outPrice: Double,var amount: Int,var note: String)

    data class Product(var productId: Int,var productName:String,var productDescription:String,
                              var productCategory:String,var productCode:String,var productImg:String,var productTags:String)

    data class Supplier_add(var supplierName: String,var companyId: Int,var supplierAddress: String, var supplierContact:String,var tags:String,var supplierCompany:String)

    data class Receiver_add(var receiverName: String,var companyId: Int,var receiverAddress: String, var receiverContact:String,var tags:String,var receiverCompany:String)


    data class Supplier(var supplierId:Int,var supplierName:String,var supplierAddress:String,var supplierContact:String, var tags:String,var supplierCompany:String)

    data class Receiver(var receiverId:Int,var receiverName:String,var receiverAddress:String,var receiverContact:String, var tags:String,var receiverCompany:String)

    data class Category(var categoryId:Int, var categoryName: String)

    data class Supplier_history(var inId: Int, var userId: Int, var userName: String, var productId:Int ,var productName: String,
                                var productImg:String, var productTags :String, var warehouseId: Int, var warehouseName: String, var amount: Int, var inPrice: Double,
                                var supplierId: Int, var supplierName: String, var note: String, var createTime: String)

    data class Receiver_history(var inId: Int, var userId: Int, var userName: String, var productId:Int ,var productName: String,
                                var productImg:String, var productTags :String, var warehouseId: Int, var warehouseName: String, var amount: Int, var outPrice: Double,
                                var receiverId: Int, var receiverName: String, var note: String, var createTime: String)

}
