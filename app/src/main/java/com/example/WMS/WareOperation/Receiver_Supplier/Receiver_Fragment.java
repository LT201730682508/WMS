package com.example.WMS.WareOperation.Receiver_Supplier;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.WMS.BaseCallback;
import com.example.WMS.Base_Topbar;
import com.example.WMS.MainActivity;
import com.example.WMS.OkHttpHelper;
import com.example.WMS.R;
import com.example.WMS.domain.DataBean;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class Receiver_Fragment extends Fragment implements View.OnClickListener{
    protected Context context;
    private static ArrayList<DataBean.Receiver> receivers_list;
    private static RecyclerView rv_pager;
    private static ImageView im_empty;
    private static TextView tv_empty;
    private Button btn_add;
    private MyHandler handler;
    private Base_Topbar base_topbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static RS_Adapter<RS_Adapter.VH> adapter;
    private static String token;
    private String roleList;
    public Receiver_Fragment(String token, String roleList) {
        this.token = token;
        this.roleList = roleList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler=new MyHandler((MainActivity) getActivity());
        context=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    private View initView() {
        View view=View.inflate(context,R.layout.fragment_receiver_supplier,null);
        base_topbar=new Base_Topbar(view,(MainActivity)getActivity(),true);
        rv_pager=view.findViewById(R.id.rv_pager);
        rv_pager.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_pager.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        im_empty=view.findViewById(R.id.im_empty);
        tv_empty=view.findViewById(R.id.tv_nomedia);
        btn_add=view.findViewById(R.id.btn_add);
        if(!roleList.contains("g")){
            btn_add.setEnabled(false);
            btn_add.setTextColor(Color.LTGRAY);
            btn_add.setBackgroundColor(Color.LTGRAY);
        }
        else{
            btn_add.setEnabled(true);
        }
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RS_Add_Dialog rs_add_dialog=new RS_Add_Dialog(context,1,token);
                rs_add_dialog.show();
            }
        });
        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /**
                 * 刷新操作在这里实现
                 * */
                initData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        receivers_list=new ArrayList<DataBean.Receiver>();
        getData();
    }

    private void getData() {
        String companyId="1";
        OkHttpHelper ok= OkHttpHelper.getInstance();
        ok.get_for_list("http://121.199.22.134:8003/api-inventory/getReceiverByCompanyId/"+companyId+"?userToken="+token,new BaseCallback<DataBean.Receiver>(){
            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println("failure"+e);
            }

            @Override
            public void onResponse(Response response) {
                System.out.println("response"+response);
            }

            @Override
            public void onSuccess_List(String resultStr) {
                Gson gson= new Gson();
                DataBean.Receiver[] wares=gson.fromJson(resultStr,DataBean.Receiver[].class);
                System.out.println("a  "+resultStr);
                System.out.println(""+wares[0]);
                for (int i=0;i<wares.length;i++){
                    receivers_list.add(wares[i]);
                }
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onSuccess(Response response, DataBean.Receiver receiver) {

            }

            @Override
            public void onError(Response response, int code, Exception e) {
                System.out.println("error"+response+e);
            }
        } );
    }

    @Override
    public void onClick(View v) {
        //todo-something
    }

    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;
        public MyHandler(MainActivity activity){
            mActivity =new WeakReference<MainActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            final MainActivity activity=mActivity.get();
            super.handleMessage(msg);
            if(activity!=null){
                if(receivers_list!=null&&receivers_list.size()>0){
                    im_empty.setVisibility(View.GONE);
                    tv_empty.setVisibility(View.GONE);
                    adapter=new RS_Adapter<RS_Adapter.VH>(receivers_list, R.layout.item_receiver_supplier, 1,activity,token);
                    rv_pager.setAdapter(adapter);
                    rv_pager.setLayoutManager(new LinearLayoutManager(activity));
                }
                else{
                    im_empty.setVisibility(View.VISIBLE);
                    tv_empty.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    //返回该framgent时刷新数据
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(isHidden()){
        }else {
            onResume();
        }
    }
}
