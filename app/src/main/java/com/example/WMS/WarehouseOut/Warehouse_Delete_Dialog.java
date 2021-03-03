package com.example.WMS.WarehouseOut;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.WMS.R;
import com.example.WMS.domain.WarehouseItem;

/**
 * 商品已存在，继续入库界面 （底部弹出）
 */
public class Warehouse_Delete_Dialog extends Dialog implements View.OnClickListener{
    protected Context context;
    private Button btn_add;
    private Button btn_cancel;
    private TextView tv_name;
    private WarehouseItem warehouseItem;
    public Warehouse_Delete_Dialog(@NonNull Context context, WarehouseItem warehouseItem) {
        super(context);
        this.context=context;
        this.warehouseItem=warehouseItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(context).inflate(R.layout.fragment_warehouse_out_delete, null);
        setContentView(contentView);
        btn_add=contentView.findViewById(R.id.btn_add);
        btn_cancel=contentView.findViewById(R.id.btn_cancel);
        btn_add.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        tv_name=contentView.findViewById(R.id.tv_name);
        tv_name.setText(warehouseItem.getProduct().getProductName());
    //    ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        //存在问题：无法水平铺满
    //    layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
       // layoutParams.width = 1300;
     //   setCancelable(true);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
     //   contentView.setLayoutParams(layoutParams);
        setCanceledOnTouchOutside(true);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
    }
    @Override
    public void onClick(View v) {
        if(v==btn_add){
            //保存刷新数据库，退出
            cancel();
        }
        else if(v==btn_cancel){
            //不保存数据库，退出
            cancel();
        }
    }

}
