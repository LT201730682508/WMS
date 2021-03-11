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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.WMS.Base_Topbar;
import com.example.WMS.MainActivity;
import com.example.WMS.Open_Album;
import com.example.WMS.R;
import com.example.WMS.custom_Dialog.take_Album_Dialog;
import com.example.WMS.domain.DataBean;

/**
 * 点击item打开的修改详情页
 * */
public class WarehouseInDetailFragment extends Fragment implements View.OnClickListener {
    protected Context context;
    private Button btn_commit;
    private TextView name;
    private TextView detail;
    private TextView price;
    private Base_Topbar base_topbar;
    private TextView size;
    private ImageView picture;
    private TextView warehouse_name;
    private String warehouseName;
    private DataBean.ProductIn productIn;
    private String product_name;
    private Dialog dialog;
    public WarehouseInDetailFragment(String warehouseName, DataBean.ProductIn productIn){
        //根据仓库名和商品名读取数据库，显示已有数据
        this.warehouseName=warehouseName;
        this.productIn=productIn;
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
        name.setText(productIn.getProductName());
        detail=view.findViewById(R.id.et_detail);
        detail.setText(productIn.getProductDescription());
        price=view.findViewById(R.id.et_price);
        price.setText(productIn.getInPrice());
        return view;
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
