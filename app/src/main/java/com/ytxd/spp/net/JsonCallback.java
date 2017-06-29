/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ytxd.spp.net;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.base.Request;
import com.ytxd.spp.base.App;
import com.ytxd.spp.util.LogUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

public abstract class JsonCallback<T> extends AbsCallback<T> {

    private Type type;
    private Class<T> clazz;

    public JsonCallback() {
    }

    public JsonCallback(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        if (null != App.user) {
            request.params("UserCode", App.user.getUserCode());//
        }
    }

    @Override
    public T convertResponse(Response response) throws Throwable {
        try {
            if (type == null) {
                if (clazz == null) {
                    // 如果没有通过构造函数传进来，就自动解析父类泛型的真实类型（有局限性，继承后就无法解析到）
                    Type genType = getClass().getGenericSuperclass();
                    type = ((ParameterizedType) genType).getActualTypeArguments()[0];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String data = null;
            data = response.body
                    ().string();
            LogUtils.e(data);
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();

            if (clazz == String.class) return (T) data;
            try {

                if (clazz != null) return JSON.parseObject(data, clazz);
                if (type != null) return JSON.parseObject(data, type);
            } catch (JsonSyntaxException ex) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
