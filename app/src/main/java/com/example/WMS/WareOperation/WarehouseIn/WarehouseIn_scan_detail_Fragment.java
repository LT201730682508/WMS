package com.example.WMS.WareOperation.WarehouseIn;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
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
import com.example.WMS.Open_Album;
import com.example.WMS.R;
import com.example.WMS.custom_Dialog.take_Album_Dialog;
import com.example.WMS.domain.DataBean;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xuexiang.xui.widget.edittext.ClearEditText;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WarehouseIn_scan_detail_Fragment extends Fragment implements View.OnClickListener {
    protected Context context;
    private DataBean.ProductIn productIn;

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
    public WarehouseIn_scan_detail_Fragment(String productCode, String token, String warehouseName, int warehouseId){
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
        View view=View.inflate(context, R.layout.fragment_scan_by_code,null);
        base_topbar=new Base_Topbar(view,(MainActivity)getActivity(),false);
        base_topbar.setTitle("详情");
        btn_commit=view.findViewById(R.id.commit);
        btn_commit.setOnClickListener(this);
        warehouse_name=view.findViewById(R.id.et_warehousename);
        warehouse_name.setText(warehouseName);
        picture=view.findViewById(R.id.iv_picture);
        picture.setOnClickListener(this);
        size=view.findViewById(R.id.et_size);
        size.setFocusable(false);
        size.setOnClickListener(this);
        price=view.findViewById(R.id.et_price);
        price.setFocusable(false);
        price.setOnClickListener(this);
        name=view.findViewById(R.id.et_name);
        detail=view.findViewById(R.id.et_detail);
        category=view.findViewById(R.id.et_category);
        btn_add=view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        category.setFocusable(false);
        category.setOnClickListener(this);
        warehouse_name.setFocusable(false);
        warehouse_name.setOnClickListener(this);
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
        okHttpHelper.get_for_object("http://121.199.22.134:8003/api-inventory/getInInventoryProductByProductCode/" + productCode + "/" + wareHouseId + "?userToken=" + token, new BaseCallback<DataBean.ProductIn>() {
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
                DataBean.ProductIn wares=gson.fromJson(resultStr,DataBean.ProductIn.class);
                size.setText(wares.getTotalAmount()+"");
                price.setText(wares.getInPrice()+"元（RMB）");
                name.setText(wares.getProductName());
                detail.setText(wares.getProductDescription());
                category.setText(wares.getProductCategory());
                setImage(getActivity(),wares.getProductImg(),picture);
            }

            @Override
            public void onSuccess(Response response, DataBean.ProductIn product) {
                productIn = product;
                size.setText(product.getTotalAmount()+"");
                price.setText(product.getInPrice()+"元（RMB）");
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
    public void sendData(Map<String,String> parms, File file, String img){
        OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();
        okHttpHelper.post_for_form("http://121.199.22.134:8003/api-inventory/modifyProductInfo?userToken="+token,parms,file,img,new BaseCallback<DataBean.ProductIn_post>(){
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
            Map<String,String> map=new HashMap<>();
            map.put("productId",productIn.getProductId()+"");
            map.put("productName",name.getText().toString());
            map.put("productDescription",detail.getText().toString());
            map.put("productCategory",category.getText().toString());
            map.put("productCode","url");
            sendData(map,saveBitmapFile(((BitmapDrawable)picture.getDrawable()).getBitmap(),"productImg"),"productImg");
            ((MainActivity)getActivity()).fragment_Manager.pop();
        }
        else if (v==picture){
            dialog.show();
        }
        else if(v==size){
            Toast.makeText(context,"请使用入库改动商品数量",Toast.LENGTH_SHORT).show();
        }
        else if(v==price){
            Toast.makeText(context,"请使用入库改动商品入库价格",Toast.LENGTH_SHORT).show();
        }
        else if(v==btn_add){
            Warehouse_Add_Fragment warehouse_add_fragment=new Warehouse_Add_Fragment(context, productIn, token);
            //activity.fragment_Manager.hide_all(warehouse_add_fragment);
            warehouse_add_fragment.show();
        }
        else if(v==category||v==warehouse_name){
            Toast.makeText(context,"当前无法修改此信息~",Toast.LENGTH_SHORT).show();
        }
    }
    public File saveBitmapFile(Bitmap bitmap, String img){
        File file = new File(requireContext().getFilesDir().getPath().toString()+img+".jpg");//将要保存图片的路径
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (Exception e) {

            }
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, bos);
            bos.flush();
            bos.close();
        } catch ( Exception e) {

        }
        return file;
    }
    public void setImage(Activity activity,String str,ImageView imageView){
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
