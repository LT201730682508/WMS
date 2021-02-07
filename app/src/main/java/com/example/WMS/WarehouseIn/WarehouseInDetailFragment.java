package com.example.WMS.WarehouseIn;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.WMS.MainActivity;
import com.example.WMS.R;

public class WarehouseInDetailFragment extends Fragment implements View.OnClickListener {
    protected Context context;
    private ImageView btn_fanhui;
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
    public View initView(){
        View view=View.inflate(context, R.layout.fragment_warehouse_in_detail,null);
        btn_fanhui=view.findViewById(R.id.fanhui);
        btn_fanhui.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View v){
        if(v==btn_fanhui){
            ((MainActivity)getActivity()).fragment_Manager.pop();
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
