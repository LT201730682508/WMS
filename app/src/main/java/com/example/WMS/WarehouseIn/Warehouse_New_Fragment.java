package com.example.WMS.WarehouseIn;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.WMS.MainActivity;
import com.example.WMS.Open_Album;
import com.example.WMS.R;
import com.example.WMS.domain.WarehouseItem;
import com.example.WMS.custom_Dialog.take_Album_Dialog;

import java.io.FileNotFoundException;

/**
 * 点击新增商品打开的输入信息页
 * */
public class Warehouse_New_Fragment extends Fragment implements View.OnClickListener{
    protected Context context;
    private Button btn_commit;
    private TextView name;
    private TextView detail;
    private TextView price;
    private TextView size;
    private ImageView picture;
    private TextView warehouse_name;
    private ImageView btn_fanhui;
    private String warehouseName;
    private Dialog dialog;
    public Warehouse_New_Fragment(String warehouseName){
        this.warehouseName=warehouseName;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
        dialog= new take_Album_Dialog(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    private View initView() {
        View view=View.inflate(context, R.layout.fragment_warehouse_in_new,null);
        btn_fanhui=view.findViewById(R.id.fanhui);
        btn_fanhui.setOnClickListener(this);
        btn_commit=view.findViewById(R.id.commit);
        btn_commit.setOnClickListener(this);
        warehouse_name=view.findViewById(R.id.et_warehousename);
        warehouse_name.setText(warehouseName);
        picture=view.findViewById(R.id.iv_picture);
        picture.setOnClickListener(this);
        size=view.findViewById(R.id.et_size);
        name=view.findViewById(R.id.et_name);
        detail=view.findViewById(R.id.et_detail);
        price=view.findViewById(R.id.et_price);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v==btn_fanhui){
            //无操作返回
            ((MainActivity)getActivity()).fragment_Manager.pop();
        }
        else if (v==btn_commit){
            //更新数据
            ((MainActivity)getActivity()).fragment_Manager.pop();
        }
        else if (v==picture){

            dialog.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}
