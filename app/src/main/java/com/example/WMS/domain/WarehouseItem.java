package com.example.WMS.domain;

import java.io.Serializable;

public class WarehouseItem implements Serializable {

    private String name;//商品名称
    private int size;//商品数量
    private int price;//商品价格
    private String warehouse_name;//存储所在的仓库名
    private String imageUrl;//商品图片
    private long duration;
    private String detail;
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public WarehouseItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getWarehouse_name() {
        return warehouse_name;
    }

    public void setWarehouse_name(String warehouse_name) {
        this.warehouse_name = warehouse_name;
    }
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    @Override
    public String toString() {
        return "MediaItem{" +
                "name='" + name +
                ", price=" + price +
                ", size=" + size +
                ", warehouse_name=" + warehouse_name +
                ", detail=" + detail +
                '}';
    }


}
