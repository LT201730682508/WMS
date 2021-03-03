package com.example.WMS.domain;

public class WarehouseItem {
    private int id;
    private int warehouseId;
    //private int inPrice;
    private int totalAmount;
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

//    private String name;//商品名称
//    private int size;//商品数量
//    private int price;//商品价格
//    private String warehouse_name;//存储所在的仓库名
//    private String imageUrl;//商品图片
//    private long duration;
//    private String detail;
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
//
//    public int getPrice() {
//        return price;
//    }
//
//    public void setPrice(int price) {
//        this.price = price;
//    }
//
//    public WarehouseItem() {
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public long getDuration() {
//        return duration;
//    }
//
//    public void setDuration(long duration) {
//        this.duration = duration;
//    }
//
//    public int getSize() {
//        return size;
//    }
//
//    public void setSize(int size) {
//        this.size = size;
//    }
//
//    public String getWarehouse_name() {
//        return warehouse_name;
//    }
//
//    public void setWarehouse_name(String warehouse_name) {
//        this.warehouse_name = warehouse_name;
//    }
//    public String getDetail() {
//        return detail;
//    }
//
//    public void setDetail(String detail) {
//        this.detail = detail;
//    }



}
