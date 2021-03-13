package com.example.WMS.Receiver_Supplier;

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
import com.example.WMS.domain.DataBean;

public class RS_Add_Dialog extends Dialog implements View.OnClickListener {
    protected Context context;
    private Button btn_add;
    private Button btn_cancel;
    private TextView title;
    private int opType;
    public RS_Add_Dialog(@NonNull Context context,int opType) {
        super(context);
        this.context=context;
        this.opType=opType;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(context).inflate(R.layout.receiver_supplier_add_dialog, null);
        setContentView(contentView);
        btn_add=contentView.findViewById(R.id.btn_add);
        btn_cancel=contentView.findViewById(R.id.btn_cancel);
        btn_add.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        title=contentView.findViewById(R.id.title);
        if(opType==0){
            title.setText("新增供应商");
        }
        else if(opType==1){
            title.setText("新增客户");
        }
        setCanceledOnTouchOutside(true);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
    }
    @Override
    public void onClick(View v) {
        if(v==btn_add){
            //保存刷新数据库，退出
            //
            cancel();
        }
        else if(v==btn_cancel){
            //不保存数据库，退出
            cancel();
        }
    }
}
