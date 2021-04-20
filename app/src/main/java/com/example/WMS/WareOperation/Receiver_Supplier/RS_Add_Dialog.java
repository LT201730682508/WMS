package com.example.WMS.WareOperation.Receiver_Supplier;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.WMS.BaseCallback;
import com.example.WMS.OkHttpHelper;
import com.example.WMS.R;
import com.example.WMS.domain.DataBean;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xuexiang.xui.widget.edittext.ClearEditText;

import java.io.IOException;

public class RS_Add_Dialog extends Dialog implements View.OnClickListener {
    protected Context context;
    private Button btn_add;
    private Button btn_cancel;
    private TextView title;
    private ClearEditText address;
    private ClearEditText name;
    private ClearEditText companyName;
    private ClearEditText contact;
    private CheckBox checkBox_1;
    private CheckBox checkBox_2;
    private CheckBox checkBox_3;
    private CheckBox checkBox_4;
    private int opType;
    private String token;

    public RS_Add_Dialog(@NonNull Context context,int opType,String token) {
        super(context);
        this.context=context;
        this.opType=opType;
        this.token=token;
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
        address=contentView.findViewById(R.id.et_address);
        name=contentView.findViewById(R.id.et_name);
        companyName = contentView.findViewById(R.id.et_company);
        contact = contentView.findViewById(R.id.et_contract);
        checkBox_1 = contentView.findViewById(R.id.checkbox1);
        checkBox_2 = contentView.findViewById(R.id.checkbox2);
        checkBox_3 = contentView.findViewById(R.id.checkbox3);
        checkBox_4 = contentView.findViewById(R.id.checkbox4);
        if(opType==0){
            title.setText("新增供应商");
            checkBox_1.setText("交货及时");
            checkBox_2.setText("可以赊账");
            checkBox_3.setText("质量上乘");
            checkBox_4.setText("质量较差");
        }
        else if(opType==1){
            title.setText("新增客户");
            checkBox_1.setText("拖欠尾款");
            checkBox_2.setText("付款及时");
            checkBox_3.setText("提货量大");
            checkBox_4.setText("提货量小");
        }
        setCanceledOnTouchOutside(true);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setWindowAnimations(R.style.BottomDialog_Animation);


    }

    public void sendSupplierData(DataBean.Supplier_add parms){
        OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();
        okHttpHelper.post_for_object("http://121.199.22.134:8003/api-inventory/addSupplier/?userToken="+token,parms,new BaseCallback<DataBean.Supplier_add>(){
            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println("failure"+e);
            }

            @Override
            public void onResponse(Response response) {
                System.out.println("@@@@@@@@@@1"+response);
            }

            @Override
            public void onSuccess_List(String resultStr) {
                System.out.println("@@@@@3"+resultStr);
            }

            @Override
            public void onSuccess(Response response, DataBean.Supplier_add supplier_add) {
                System.out.println("@@@@@3"+response);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                System.out.println("error"+response+e);
            }

        });
    }

    public void sendReceiverData(DataBean.Receiver_add parms){
        OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();
        okHttpHelper.post_for_object("http://121.199.22.134:8003/api-inventory/addReceiver/?userToken="+token,parms,new BaseCallback<DataBean.Receiver_add>(){
            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println("failure"+e);
            }

            @Override
            public void onResponse(Response response) {
                System.out.println("@@@@@@@@@@1"+response);
            }

            @Override
            public void onSuccess_List(String resultStr) {
                System.out.println("@@@@@3"+resultStr);
            }

            @Override
            public void onSuccess(Response response, DataBean.Receiver_add receiver_add) {
                System.out.println("@@@@@3"+response);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                System.out.println("error"+response+e);
            }
        });
    }

    @Override
    public void onClick(View v) {
        String str = "";
        if(checkBox_1.isChecked()){
            str += "a";
        }
        if(checkBox_2.isChecked()){
            str += "b";
        }
        if(checkBox_3.isChecked()){
            str += "c";
        }
        if(checkBox_4.isChecked()){
            str += "d";
        }

        if(v==btn_add){
            if(opType==0){
                DataBean.Supplier_add post_data=new DataBean.Supplier_add(name.getText().toString(),
                        "1",address.getText().toString(), contact.getText().toString(), str, companyName.getText().toString());
                sendSupplierData(post_data);
            }
            else if(opType==1){
                DataBean.Receiver_add post_data=new DataBean.Receiver_add(name.getText().toString(),
                        "1",address.getText().toString(), contact.getText().toString(), str, companyName.getText().toString());
                sendReceiverData(post_data);
            }
            cancel();
        }
        else if(v==btn_cancel){
            //不保存数据库，退出
            cancel();
        }
    }
}
