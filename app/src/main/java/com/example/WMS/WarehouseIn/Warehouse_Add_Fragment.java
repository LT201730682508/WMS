package com.example.WMS.WarehouseIn;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.WMS.R;

/**
 * 商品已存在，继续入库界面 （底部弹出）
 */
public class Warehouse_Add_Fragment extends Fragment implements View.OnClickListener{
    protected Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    private View initView() {
        View view=View.inflate(context, R.layout.fragment_warehouse_in_detail,null);
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
