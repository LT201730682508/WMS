package com.example.WMS.WareOperation.WarehouseOut;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.WMS.BaseCallback;
import com.example.WMS.OkHttpHelper;
import com.example.WMS.R;
import com.example.WMS.domain.DataBean;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xuexiang.xui.widget.edittext.ClearEditText;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;
import com.xuexiang.xui.widget.textview.label.LabelTextView;

import java.io.IOException;

/**
 * 商品已存在，继续入库界面 （底部弹出）
 */
public class Warehouse_Delete_Dialog extends Dialog implements View.OnClickListener{
    protected Context context;
    private Button btn_add;
    private Button btn_cancel;
    private TextView tv_name;
    private ClearEditText et_size;
    private ClearEditText et_price;
    private MultiLineEditText et_note;
    private LabelTextView tv_receiver;
    private String receiverName;
    private String token;
    private DataBean.ProductOut productOut;
    private String id;
    private ImageView imageView;

    public Warehouse_Delete_Dialog(@NonNull Context context, DataBean.ProductOut productOut,String token) {
        super(context);
        this.context=context;
        this.productOut=productOut;
        this.token=token;

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
        tv_name.setText(productOut.getProductName());
        et_size=contentView.findViewById(R.id.et_size);
        tv_receiver=contentView.findViewById(R.id.select_receiver);
        SharedPreferences preferences=context.getSharedPreferences("receiver", Context.MODE_PRIVATE);
        receiverName=preferences.getString("receiverName", "defaultname");
        tv_receiver.setText(receiverName);
        et_price=contentView.findViewById(R.id.et_price);
        et_price.setText(productOut.getOutPrice()+"");
        et_note=contentView.findViewById(R.id.et_note);
        imageView=contentView.findViewById(R.id.iv_picture);
        Glide.with(context).load(productOut.getProductImg()).into(imageView);
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
            if(et_size.getText().toString()==""||et_price.getText().toString()==""){
                cancel();
            }
            else if(receiverName == "defaultname"){
                Toast.makeText(context,"请先选择客户",Toast.LENGTH_SHORT).show();
                cancel();
            }
            else if(productOut.getTotalAmount()<Integer.parseInt(et_size.getText().toString())){
                Toast.makeText(context,"库存不足！",Toast.LENGTH_SHORT).show();
                cancel();
            }
            else {
                SharedPreferences preferences=context.getSharedPreferences("receiver", Context.MODE_PRIVATE);
                id=preferences.getString("receiverId", "-1");
                DataBean.ProductOut_outWarehouse post_data=new DataBean.ProductOut_outWarehouse(productOut.getId(),id,
                        Integer.parseInt(et_price.getText().toString()),Integer.parseInt(et_size.getText().toString()),et_note.getContentText().toString());
                sendData(post_data);
                cancel();
            }

        }
        else if(v==btn_cancel){
            //不保存数据库，退出
            cancel();
        }
    }

    public void sendData(DataBean.ProductOut_outWarehouse parms){
        OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();
        okHttpHelper.post_for_object("http://121.199.22.134:8003/api-inventory/outWarehouse/?userToken="+token,parms,new BaseCallback<DataBean.ProductOut_outWarehouse>(){
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
            public void onSuccess(Response response, DataBean.ProductOut_outWarehouse productOut_outWarehouse) {
                System.out.println("@@@@@3"+response);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                System.out.println("error"+response+e);
            }
        });
    }
}
