package com.example.WMS.WarehouseIn;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
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
import com.example.WMS.BaseCallback;
import com.example.WMS.Base_Topbar;
import com.example.WMS.MainActivity;
import com.example.WMS.OkHttpHelper;
import com.example.WMS.Open_Album;
import com.example.WMS.R;

import com.example.WMS.custom_Dialog.take_Album_Dialog;
import com.example.WMS.domain.DataBean;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xuexiang.xui.widget.edittext.ClearEditText;
import com.xuexiang.xui.widget.toast.XToast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * 点击新增商品打开的输入信息页
 * */
public class Warehouse_New_Fragment extends Fragment implements View.OnClickListener{
    protected Context context;
    private Button btn_commit;
    private ClearEditText name;
    private ClearEditText detail;
    private ClearEditText category;
    private ClearEditText size;
    private Base_Topbar base_topbar;
    private ImageView picture;
    private ClearEditText warehouse_name;
    private String warehouseName;
    private int warehouseId;
    private Dialog dialog;
    public Warehouse_New_Fragment(String warehouseName,int warehouseId){
        this.warehouseName=warehouseName;
        this.warehouseId=warehouseId;
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
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v==btn_commit){
            //更新数据
            //需要更新数据库信息代码
            // /api-inventory/addProduct
            if(detail.getText().toString().equals("")||name.getText().toString().equals("")||category.getText().toString().equals("")||size.getText().toString().equals("")){
                XToast.warning(requireContext(), "请输入完整信息").show();
            }
            else{
                /**
                 * (var warehouseId:Int,var productName:String,var productDescription:String,
                 *                          var productCategory:String,var productCode:String,var productImg:String)
                 * */
                DataBean.ProductIn_post post_data=new DataBean.ProductIn_post(name.getText().toString(),detail.getText().toString(),category.getText().toString(),"productCode_bigmelon","productCode_bigmelon");
                sendData(post_data);
                XToast.success(requireContext(), "入库成功").show();
                ((MainActivity)getActivity()).fragment_Manager.pop();

            }

        }
        else if (v==picture){

            dialog.show();
        }
    }
    public void sendData(DataBean.ProductIn_post parms){
        OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();
        okHttpHelper.post_for_object("http://121.199.22.134:8003/api-inventory/addProduct/"+warehouseId+"/",parms,new BaseCallback<DataBean.ProductIn_post>(){
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

//            override fun onSuccess(response: Response?, t: String?) {
//                println("@@@@@3"+t)
//                var hander = Handler(Looper.myLooper()!!)
//                hander.post{
//                    show.show(t!!)
//                }
//            }

            @Override
            public void onError(Response response, int code, Exception e) {
                System.out.println("error"+response+e);
            }

        });
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
