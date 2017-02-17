package com.exz.antcargo.activity.okhttp;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.callback.Callback;

import org.xutils.common.util.ParameterizedTypeUtil;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import okhttp3.Response;

public abstract class GenericsCallback<T> extends Callback<T> {

    @Override
    public T parseNetworkResponse(Response response, int id) throws IOException {
        String string = response.body().string();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (entityClass == String.class) {
            return (T) string;
        }
        T bean;
        if (entityClass == List.class) {
            // fastJson 解析:
            bean= (T) JSON.parseArray(string, (Class<?>) ParameterizedTypeUtil.getParameterizedType(entityClass, List.class, 0));
        } else {
            bean= JSON.parseObject(string, entityClass);
        }
        return bean;
    }

}