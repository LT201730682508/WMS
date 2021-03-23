package com.example.WMS.WarehouseIn;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.example.WMS.BaseCallback;
import com.example.WMS.MainActivity;
import com.example.WMS.OkHttpHelper;
import com.example.WMS.R;
import com.example.WMS.domain.DataBean;
import com.example.WMS.domain.DataBean.ProductIn;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xuexiang.xui.widget.edittext.ClearEditText;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;
import com.xuexiang.xui.widget.textview.label.LabelTextView;
import com.xuexiang.xui.widget.toast.XToast;

import java.io.IOException;

/**
 * 商品已存在，继续入库界面 （底部弹出）
 */
public class Warehouse_Add_Fragment extends Dialog implements View.OnClickListener{
    protected Context context;
    private Button btn_add;
    private Button btn_cancel;
    private TextView tv_name;
    private ClearEditText et_size;
    private ClearEditText et_price;
    private MultiLineEditText et_note;
    private DataBean.ProductIn productIn;
    private LabelTextView tv_supplier;
    private String supplierName;
    private String token;
    private String id;
    public Warehouse_Add_Fragment(Context context,DataBean.ProductIn productIn,String token) {
        super(context);
        this.context=context;
        this.productIn=productIn;
        this.token=token;
    }


//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater=getActivity().getLayoutInflater();
//        View view=inflater.inflate(R.layout.fragment_warehouse_in_add,null);
//        btn_add=view.findViewById(R.id.btn_add);
//        btn_cancel=view.findViewById(R.id.btn_cancel);
//        btn_add.setOnClickListener(this);
//        btn_cancel.setOnClickListener(this);
//        tv_name=view.findViewById(R.id.tv_name);
//        tv_name.setText(productIn.getProductName());
//        et_size=view.findViewById(R.id.et_size);
//        et_size.setText(productIn.getInPrice());
//        et_price=view.findViewById(R.id.et_price);
//        et_note=view.findViewById(R.id.et_note);
//        tv_supplier=view.findViewById(R.id.select_supplier);
//        tv_supplier.setText(supplierName);
//        builder.setView(view);
//        return builder.create();
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(context).inflate(R.layout.fragment_warehouse_in_add, null);
        setContentView(contentView);
        btn_add=contentView.findViewById(R.id.btn_add);
        btn_cancel=contentView.findViewById(R.id.btn_cancel);
        btn_add.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        tv_name=contentView.findViewById(R.id.tv_name);
        tv_name.setText(productIn.getProductName());
        et_size=contentView.findViewById(R.id.et_size);
        tv_supplier=contentView.findViewById(R.id.select_supplier);
        SharedPreferences preferences=context.getSharedPreferences("supplier", Context.MODE_PRIVATE);
        supplierName=preferences.getString("supplierName", "defaultname");
        tv_supplier.setText(supplierName);
        et_price=contentView.findViewById(R.id.et_price);
        et_price.setText(productIn.getInPrice()+"");
        et_note=contentView.findViewById(R.id.et_note);
        //btn_select=contentView.findViewById(R.id.select_supplier);
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
            SharedPreferences preferences=context.getSharedPreferences("supplier", Context.MODE_PRIVATE);
            id=preferences.getString("supplierId", "-1");
            DataBean.ProductIn_inWarehouse post_data=new DataBean.ProductIn_inWarehouse(productIn.getProductId(),id,
                    Integer.parseInt(et_price.getText().toString()),Integer.parseInt(et_size.getText().toString()),et_note.getContentText().toString());
            sendData(post_data);

            cancel();
            //((MainActivity)getActivity()).fragment_Manager.pop();
        }
        else if(v==btn_cancel){
            //不保存数据库，退出
            cancel();
            //((MainActivity)getActivity()).fragment_Manager.pop();
        }

    }
    public void sendData(DataBean.ProductIn_inWarehouse parms){
        OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();
        okHttpHelper.post_for_object("http://121.199.22.134:8003/api-inventory/inWarehouse/?userToken="+token,parms,new BaseCallback<DataBean.ProductIn_inWarehouse>(){
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
            public void onSuccess(Response response, DataBean.ProductIn_inWarehouse productIn_inWarehouse) {
                System.out.println("@@@@@3"+response);
            }


            @Override
            public void onError(Response response, int code, Exception e) {
                System.out.println("error"+response+e);
            }

        });
    }

}
