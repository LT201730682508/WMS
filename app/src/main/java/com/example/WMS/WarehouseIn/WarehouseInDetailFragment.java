package com.example.WMS.WarehouseIn;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.WMS.BaseCallback;
import com.example.WMS.Base_Topbar;
import com.example.WMS.MainActivity;
import com.example.WMS.My_Thread;
import com.example.WMS.OkHttpHelper;
import com.example.WMS.Open_Album;
import com.example.WMS.R;
import com.example.WMS.custom_Dialog.take_Album_Dialog;
import com.example.WMS.domain.DataBean;
import com.example.WMS.execute_IO;
import com.example.WMS.perform_UI;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xuexiang.xui.widget.edittext.ClearEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 点击item打开的修改详情页
 * */
public class WarehouseInDetailFragment extends Fragment implements View.OnClickListener {
    protected Context context;
    private Button btn_commit;
    private ClearEditText name;
    private ClearEditText detail;
    private ClearEditText category;
    private Base_Topbar base_topbar;
    private ClearEditText size;
    private ImageView picture;
    private ClearEditText warehouse_name;
    private String warehouseName;
    private DataBean.ProductIn productIn;
    private String product_name;
    private Dialog dialog;
    private DataBean.Product wares;
    private String token;
    public WarehouseInDetailFragment(String warehouseName, DataBean.ProductIn productIn,String token){
        //根据仓库名和商品名读取数据库，显示已有数据
        this.warehouseName=warehouseName;
        this.productIn=productIn;
        this.token=token;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
        dialog= new take_Album_Dialog(context);
        getData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    private View initView() {
        View view=View.inflate(context, R.layout.fragment_warehouse_in_detail,null);

        base_topbar=new Base_Topbar(view,(MainActivity)getActivity(),false);
        base_topbar.setTitle("入库");
        btn_commit=view.findViewById(R.id.commit);
        btn_commit.setOnClickListener(this);
        warehouse_name=view.findViewById(R.id.et_warehousename);
        warehouse_name.setText(warehouseName);
        picture=view.findViewById(R.id.iv_picture);
        picture.setOnClickListener(this);
        size=view.findViewById(R.id.et_size);
        name=view.findViewById(R.id.et_name);
        detail=view.findViewById(R.id.et_detail);
        category=view.findViewById(R.id.et_category);
        getData();
        size.setFocusable(false);
        size.setOnClickListener(this);
        size.setText(""+productIn.getTotalAmount());
        return view;
    }

    private void getData() {
        OkHttpHelper ok= OkHttpHelper.getInstance();
        ok.get_for_list("http://121.199.22.134:8003/api-inventory/getProductById/"+productIn.getProductId()+"?userToken="+token,new BaseCallback<DataBean.ProductIn>(){

            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println("failure"+e);
            }

            @Override
            public void onResponse(Response response) {
                System.out.println("@@@@@@@@@@1"+response);
            }

            @Override


            public void onSuccess_List(final String resultStr) {
                Gson gson= new Gson();
                wares=gson.fromJson(resultStr,DataBean.Product.class);
                System.out.println("@@@@@@@@@@2"+resultStr);
                category.setText(wares.getProductCategory());
                detail.setText(wares.getProductDescription());
                name.setText(wares.getProductName());

            }

            @Override
            public void onSuccess(Response response, DataBean.ProductIn productIn) {

                System.out.println("Success"+response);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                System.out.println("error"+response+e);
            }
        } );
    }
    public void sendData(DataBean.ProductIn_post parms){
        OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();
        okHttpHelper.post_for_object("http://121.199.22.134:8003/api-inventory/addProduct/"+"/",parms,new BaseCallback<DataBean.ProductIn_post>(){
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
            public void onSuccess(Response response, DataBean.ProductIn_post productIn_post) {
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
        if (v==btn_commit){
            //更新数据
            //需要更新数据库信息代码
            // /api-inventory/modifyProduct
            ((MainActivity)getActivity()).fragment_Manager.pop();
        }
        else if (v==picture){

            dialog.show();
        }
        else if(v==size){
            Toast.makeText(context,"请使用入库改动商品数量",Toast.LENGTH_SHORT).show();
        }
    }
    public   void setImage(Activity activity,String str,ImageView imageView){
        Glide.with(activity).load(str).into(imageView);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Open_Album.TAKE_PHOTO:{
                if (resultCode == Activity.RESULT_OK){
                    try {
                        setImage(getActivity(),Open_Album.uri.toString(),picture);
                    }catch (Exception e){

                    }
                    break;
                }
            }
            case  Open_Album.CHOOSE_PHOTO:{
                if(resultCode == Activity.RESULT_OK){
                    if (Build.VERSION.SDK_INT >= 19) Open_Album.handleImageOnKitKat(getActivity(),data,picture);
                    else Open_Album.handleImageBeforeKitKat(getActivity(),data,picture);

                }else {}
                break;
            }
            default:{
                break;
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(isHidden()){
        }else {
            onResume();
        }
    }
}
