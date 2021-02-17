package com.example.WMS.WarehouseIn;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;


import com.example.WMS.R;

/**
 * 商品已存在，继续入库界面 （底部弹出）
 */
public class Warehouse_Add_Fragment extends Dialog implements View.OnClickListener{
    protected Context context;
    private Button btn_add;
    private Button btn_cancel;
    public Warehouse_Add_Fragment(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(context).inflate(R.layout.fragment_warehouse_in_add, null);
        setContentView(contentView);
        btn_add=contentView.findViewById(R.id.btn_add);
        btn_cancel=contentView.findViewById(R.id.btn_cancel);
        btn_add.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        //存在问题：无法水平铺满
        //layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        layoutParams.width = 1300;
        contentView.setLayoutParams(layoutParams);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
    }
    @Override
    public void onClick(View v) {
        if(v==btn_add){

        }
        else if(v==btn_cancel){

        }
    }

}
