package com.example.WMS.WareOperation.Categroy;

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

import com.example.WMS.BaseCallback;
import com.example.WMS.OkHttpHelper;
import com.example.WMS.R;
import com.example.WMS.domain.DataBean;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xuexiang.xui.widget.edittext.ClearEditText;

import java.io.IOException;

public class Category_Add_Dialog extends Dialog implements View.OnClickListener {
    protected Context context;
    private Button btn_add;
    private Button btn_cancel;
    private TextView title;
    private ClearEditText name;
    private String token;
    private int warehouseId;
    public Category_Add_Dialog(@NonNull Context context, String token, int warehouseId) {
        super(context);
        this.context=context;
        this.token=token;
        this.warehouseId = warehouseId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(context).inflate(R.layout.category_add_dialog, null);
        setContentView(contentView);
        btn_add=contentView.findViewById(R.id.btn_add);
        btn_cancel=contentView.findViewById(R.id.btn_cancel);
        btn_add.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        title=contentView.findViewById(R.id.title);
        name=contentView.findViewById(R.id.et_name);
        title.setText("新增种类");
        setCanceledOnTouchOutside(true);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
    }

    public void sendCategoryData(String categoryName){
        OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();
        okHttpHelper.post_for_object("http://121.199.22.134:8003/api-inventory/addCategory/"+categoryName+"/"+warehouseId+"/?userToken="+token,null,new BaseCallback<String>(){
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
            public void onSuccess(Response response, String string) {
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
        if(v==btn_add){
            sendCategoryData(name.getText().toString());
            cancel();
        }
        else if(v==btn_cancel){
            //不保存数据库，退出
            cancel();
        }
    }
}
