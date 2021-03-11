package com.example.WMS;


import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OkHttpHelper<T> {

    private static OkHttpClient okHttpClient;
    private static OkHttpHelper okHttpHelper;
    private Gson gson;

    private Handler handler;

    //OkHttpHelper的构造方法
    private OkHttpHelper(){
        okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(10,TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(10,TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(10,TimeUnit.SECONDS);
        gson = new Gson();
        handler = new Handler(Looper.getMainLooper());

    };

    //构建一个OkHttpHelper实例
    public static OkHttpHelper getInstance(){
       if(okHttpHelper==null){
           okHttpHelper=new OkHttpHelper();
       }
        return  okHttpHelper;
    }

    //Get方法
    public void get_for_object(String url,BaseCallback callback){
        Request request = buildRequest(url,null,HttpMethodType.GET);
        doRequest(request,callback);
    }
    public void get_for_list(String url, BaseCallback callback){
        Request request = buildRequest(url,null,HttpMethodType.GET);
        doRequest_for_list(request,callback);
    }
    //Post方法
    public void post_for_object(String url, Object params,BaseCallback callback){

        Request request = buildRequest(url,params,HttpMethodType.POST);
        doRequest(request,callback);
    }
    public void post_for_list(String url, Object params,BaseCallback callback){
        Request request = buildRequest(url,params,HttpMethodType.POST);
        doRequest_for_list(request,callback);
    }
    //构建Request的方法
    private  Request buildRequest(String url,Object params,HttpMethodType methodType){

        //构建一个Request的对象
        Request.Builder builder = new Request.Builder();
        builder.url(url);

        if (methodType == HttpMethodType.GET){
            builder.get();
        }
        else if (methodType == HttpMethodType.POST){
            RequestBody body = buildFormData(params);
            builder.post(body);
        }
        return builder.build();
    }


    //构建RequestBody的方法
    private RequestBody buildFormData(Object params){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String jsonStr=gson.toJson(params);
        RequestBody body = RequestBody.create(JSON, jsonStr);
        return body;
    }

    //用来回调的方法
    private void callbackSuccess(final BaseCallback callback,final Response response,final Object object){

        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response,object);
            }
        });
    }
    private void callbackSuccess_for_List(final BaseCallback callback,final String result){

        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess_List(result);
            }
        });
    }
    //实现一个枚举类,用来判断使用Get方法还是使用Post方法
    enum HttpMethodType{
        GET,
        POST
    }

    //传入自定义的Callback
    public void doRequest(Request request, final BaseCallback callback){

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callback.onFailure(request,e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                callback.onResponse(response);
                if (response.isSuccessful()){
                    String resultStr = response.body().string();

                        //使用try catch处理json解析错误
                        try {
                            Object object = gson.fromJson(resultStr,callback.mType);
                            callbackSuccess(callback,response,object);
                        }catch (JsonParseException e){
                            callback.onError(response,response.code(),e);
                        }
                    }
                else{
                    callback.onError(response,response.code(),null);
                }
            }
        });
    }

    public void doRequest_for_list(Request request, final BaseCallback callback){

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callback.onFailure(request,e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String resultStr = response.body().string();
                callback.onResponse(response);
                if (response.isSuccessful()){
                    callbackSuccess_for_List(callback,resultStr);
                }

                else{
                    callback.onError(response,response.code(),null);
                }
            }
        });
    }



}