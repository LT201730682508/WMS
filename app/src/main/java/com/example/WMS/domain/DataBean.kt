package com.example.WMS.domain

class DataBean {
    //出入库按钮下的的databean
    data class ProductIn(var id:Int,var productId:Int,var productName:String,var totalAmount:Int,var productDescription:String,
                         var inPrice:Int,var productCategory:String,var productCode:String,var productImg:String,var updateTime:String, var productTags:String)

    data class ProductOut(var id:Int,var productId:Int,var productName:String,var totalAmount:Int,var productDescription:String,
                        var outPrice:Int,var productCategory:String,var productCode:String,var productImg:String,var updateTime:String, var productTags:String)

    data class ProductIn_post(var productId: Int ,var productName: String, var productDescription: String,
                              var productCategory: String, var productCode: String, var productImg: String, var productTags:String)

    data class ProductIn_inWarehouse(var id:Int,var supplierId: String,var inPrice: Double,var amount: Int,var note: String)

    data class ProductOut_outWarehouse(var id:Int,var receiverId: String,var outPrice: Double,var amount: Int,var note: String)

    data class Product(var productId: Int,var productName:String,var productDescription:String,
                              var productCategory:String,var productCode:String,var productImg:String,var productTags:String)

    data class Supplier_add(var supplierName: String,var companyId: String,var supplierAddress: String, var supplierContact:String,var tags:String,var supplierCompany:String)

    data class Receiver_add(var receiverName: String,var companyId: String,var receiverAddress: String, var receiverContact:String,var tags:String,var receiverCompany:String)


    data class Supplier(var supplierId:Int,var supplierName:String,var supplierAddress:String,var supplierContact:String,var tags:String,var supplierCompany:String)

    data class Receiver(var receiverId:Int,var receiverName:String,var receiverAddress:String,var receiverContact:String,var tags:String,var receiverCompany:String)

    data class Category(var categoryId:Int, var categoryName: String)


}
