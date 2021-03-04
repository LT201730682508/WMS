package com.example.WMS;


import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseCallback<T> {

    public Type mType;
    //将泛型 T 转换成 mType
    static Type getSuperclassTypeParameter(Class<?> subclass){
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class){
            throw new RuntimeException("Missing type parameter");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);

    }

    public BaseCallback(){
        mType = getSuperclassTypeParameter(getClass());
    }

    //请求前调用的方法
    //定义抽象方法给子类实现


    public abstract void onFailure(Request request, IOException e);

    public abstract void onResponse(Response response);

    public abstract void onSuccess_List(String resultStr);
    public abstract void onSuccess (Response response,T t);

    //code是http的状态码，用来提示信息
    public abstract void onError (Response response,int code,Exception e);
}