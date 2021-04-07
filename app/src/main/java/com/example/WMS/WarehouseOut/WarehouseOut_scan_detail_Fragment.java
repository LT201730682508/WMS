package com.example.WMS.WarehouseOut;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.WMS.BaseCallback;
import com.example.WMS.Base_Topbar;
import com.example.WMS.MainActivity;
import com.example.WMS.OkHttpHelper;
import com.example.WMS.R;
import com.example.WMS.custom_Dialog.take_Album_Dialog;
import com.example.WMS.domain.DataBean;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xuexiang.xui.widget.edittext.ClearEditText;

import java.io.IOException;


public class WarehouseOut_scan_detail_Fragment extends Fragment implements View.OnClickListener {
    protected Context context;
    private DataBean.ProductOut productOut;

    private Button btn_commit;
    private ClearEditText name;
    private ClearEditText detail;
    private ClearEditText category;
    private Base_Topbar base_topbar;
    private ClearEditText size;
    private ClearEditText price;
    private ClearEditText warehouse_name;
    private ImageView picture;
    private Button btn_add;
    private Dialog dialog;
    private String warehouseName;
    private String token;
    private String productCode;
    private int wareHouseId;
    public WarehouseOut_scan_detail_Fragment(String productCode, String token, String warehouseName, int warehouseId){
        this.productCode = productCode;
        this.token = token;
        this.warehouseName = warehouseName;
        this.wareHouseId = warehouseId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        dialog= new take_Album_Dialog(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    private View initView() {
        View view=View.inflate(context, R.layout.fragment_scan_by_code_out,null);
        base_topbar=new Base_Topbar(view,(MainActivity)getActivity(),false);
        base_topbar.setTitle("详情");
        btn_commit=view.findViewById(R.id.commit);
        btn_commit.setOnClickListener(this);
        warehouse_name=view.findViewById(R.id.et_warehousename);
        warehouse_name.setText(warehouseName);
        warehouse_name.setFocusable(false);
        warehouse_name.setOnClickListener(this);
        picture=view.findViewById(R.id.iv_picture);
        picture.setOnClickListener(this);
        picture.setFocusable(false);
        picture.setOnClickListener(this);
        size=view.findViewById(R.id.et_size);
        size.setFocusable(false);
        size.setOnClickListener(this);
        price=view.findViewById(R.id.et_price);
        price.setFocusable(false);
        price.setOnClickListener(this);
        name=view.findViewById(R.id.et_name);
        name.setFocusable(false);
        name.setOnClickListener(this);
        detail=view.findViewById(R.id.et_detail);
        detail.setFocusable(false);
        detail.setOnClickListener(this);
        category=view.findViewById(R.id.et_category);
        category.setFocusable(false);
        category.setOnClickListener(this);
        btn_add=view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        getByCode();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getByCode();
    }

    private void getByCode() {
        OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();
        okHttpHelper.get_for_object("http://121.199.22.134:8003/api-inventory/getOutInventoryProductByProductCode/" + productCode + "/" + wareHouseId + "?userToken=" + token, new BaseCallback<DataBean.ProductOut>() {
            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println("failure" + e);
            }

            @Override
            public void onResponse(Response response) {
                System.out.println("@@@@@@@@@@1" + response);
            }

            @Override
            public void onSuccess_List(String resultStr) {
                Gson gson= new Gson();
                DataBean.ProductOut wares=gson.fromJson(resultStr,DataBean.ProductOut.class);
                size.setText(wares.getTotalAmount()+"");
                price.setText(wares.getOutPrice()+"");
                name.setText(wares.getProductName());
                detail.setText(wares.getProductDescription());
                category.setText(wares.getProductCategory());
                setImage(getActivity(),wares.getProductImg(),picture);
            }

            @Override
            public void onSuccess(Response response, DataBean.ProductOut product) {
                productOut = product;
                size.setText(product.getTotalAmount()+"");
                price.setText(product.getOutPrice()+"");
                name.setText(product.getProductName());
                detail.setText(product.getProductDescription());
                category.setText(product.getProductCategory());
                setImage(getActivity(),product.getProductImg(),picture);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                System.out.println("error" + response + e);
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (v==btn_commit){
            //更新数据
            //需要更新数据库信息代码
            // /api-inventory/modifyProduct
//            Map<String,String> map=new HashMap<>();
//            map.put("productId",productOut.getProductId()+"");
//            map.put("productName",name.getText().toString());
//            map.put("productDescription",detail.getText().toString());
//            map.put("productCategory",category.getText().toString());
//            map.put("productCode","url");
//            sendData(map,saveBitmapFile(((BitmapDrawable)picture.getDrawable()).getBitmap(),"productImg"),"productImg");
            ((MainActivity)getActivity()).fragment_Manager.pop();
        }
        else if (v==picture){
            dialog.show();
        }
        else if(v==size){
            Toast.makeText(context,"请使用出库改动商品数量",Toast.LENGTH_SHORT).show();
        }
        else if(v==price){
            Toast.makeText(context,"请使用出库改动商品入库价格",Toast.LENGTH_SHORT).show();
        }
        else if(v==name||v==warehouse_name||v==detail||v==category){
            Toast.makeText(context,"请在入库模块修改商品信息",Toast.LENGTH_SHORT).show();
        }
        else if(v==btn_add){
            Warehouse_Delete_Dialog warehouse_delete_dialog=new Warehouse_Delete_Dialog(context, productOut, token);
            warehouse_delete_dialog.show();
        }
    }
    public void setImage(Activity activity,String str,ImageView imageView){
        Glide.with(activity).load(str).into(imageView);
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
