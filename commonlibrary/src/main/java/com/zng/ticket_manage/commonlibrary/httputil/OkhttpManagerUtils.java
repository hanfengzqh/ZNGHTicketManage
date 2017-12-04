package com.zng.ticket_manage.commonlibrary.httputil;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.zng.ticket_manage.commonlibrary.utils.NetworkUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OkhttpManagerUtils {
    private OkHttpClient client;
    private static OkhttpManagerUtils okhttpManagerUtils;
    private Handler mHandler;

    /**
     * 单例模式 OKhttpManager实例
     */
    private static OkhttpManagerUtils getInstance() {
        if (okhttpManagerUtils == null) {
            okhttpManagerUtils = new OkhttpManagerUtils();
        }
        return okhttpManagerUtils;
    }

    private OkhttpManagerUtils() {
        client = new OkHttpClient.Builder()
                .connectTimeout(60,TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .build();
        mHandler = new Handler(Looper.getMainLooper());
    }


    //******************  内部逻辑处理方法  ******************/
    private Response p_getSync(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response;
    }

    private String p_getSyncAsString(String url) throws IOException {
        return p_getSync(url).body().string();
    }

    private void p_getAsync(final Context context, String url, final DataCallBack callBack) {
        if (!NetworkUtil.checkNetwork(context)){
        	String result = "{\"result\":false,\"msg\":\"网络连接不可用请检查!\"}";
            deliverDataSuccess(result, callBack);
            return;
        }
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String result = response.body().string();
//                    deliverDataSuccess(result, callBack);
                    deliverDataSuccess(result, callBack);
                } catch (IOException e) {
                    e.printStackTrace();
                    deliverDataFailure(request, e, callBack);
                }
            }
        });
    }

    private void p_getHeaderAsync(final Context context, String header, String url, final DataCallBack callBack) {
        if (!NetworkUtil.checkNetwork(context)){
            String result = "{\"result\":false,\"msg\":\"网络连接不可用请检查!\"}";
            deliverDataSuccess(result, callBack);
            return;
        }
        final Request request = new Request.Builder().addHeader("token",header).url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String result = response.body().string();
//                    deliverDataSuccess(result, callBack);
                    deliverDataSuccess(result, callBack);
                } catch (IOException e) {
                    e.printStackTrace();
                    deliverDataFailure(request, e, callBack);
                }
            }
        });
    }
    
    private void p_postAsync(final Context context, String url, Map<String, String> params, final DataCallBack callBack) {
        if (!NetworkUtil.checkNetwork(context)){
            String result = "{\"result\":false,\"msg\":\"网络连接不可用请检查!\"}";
            deliverDataSuccess(result, callBack);
            return;
        }
        RequestBody requestBody = null;
        if (params == null) {
            params = new HashMap<String, String>();
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey().toString();
            String value = null;
            if (entry.getValue() == null) {
                value = "";
            } else {
                value = entry.getValue().toString();
            }
            builder.add(key, value);
        }
        requestBody = builder.build();
        final Request request = new Request.Builder().url(url).post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                	
                    String result = response.body().string();
                    deliverDataSuccess(result, callBack);
                } catch (IOException e) {
                    e.printStackTrace();
                    deliverDataFailure(request, e, callBack);
                }
            }
        });
    }

    private void p_postHeaderAsync(final Context context, String token, String url, Map<String, String> params, final DataCallBack callBack) {
        if (!NetworkUtil.checkNetwork(context)){
        	String result =  "{\"result\":false,\"msg\":\"网络连接不可用请检查!\"}";
            deliverDataSuccess(result, callBack);
            return;
        }
        RequestBody requestBody = null;
        if (params == null) {
            params = new HashMap<String, String>();
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey().toString();
            String value = null;
            if (entry.getValue() == null) {
                value = "";
            } else {
                value = entry.getValue().toString();
            }
            builder.add(key, value);
        }
        requestBody = builder.build();
        final Request request = new Request.Builder().addHeader("token",token).url(url).post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    deliverDataSuccess(result, callBack);
                } catch (IOException e) {
                    e.printStackTrace();
                    deliverDataFailure(request, e, callBack);
                }
            }
        });
    }

    private void p_postHeaderFileAsync(final Context context, String token,String url, String filePath, String key, Map<String, String> params, final DataCallBack callBack){
        if (!NetworkUtil.checkNetwork(context)){
            String result =  "{\"result\":false,\"msg\":\"网络连接不可用请检查!\"}";
            deliverDataSuccess(result, callBack);
            return;
        }
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        File file = new File(filePath);
        if(params != null){
            for(Map.Entry entry : params.entrySet()){
                requestBody.addFormDataPart(String.valueOf(entry.getKey()),String.valueOf(entry.getValue()));
            }
        }
        if(file != null){
            RequestBody body = RequestBody.create(MediaType.parse("*"),file);
            String fileName = file.getName();
            requestBody.addFormDataPart(key,file.getName(),body);
        }
        final Request request = new Request.Builder().addHeader("token",token).post(requestBody.build()).url(url).build();

        Call call = client.newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    deliverDataSuccess(result, callBack);
                    Log.e("onResponse===",result);
                } catch (IOException e) {
                    e.printStackTrace();
                    deliverDataFailure(request, e, callBack);
                }
            }
        });
    }

    private void p_postFileAsync(final Context context, String url, String filePath, String key, Map<String, String> params, final DataCallBack callBack){
        if (!NetworkUtil.checkNetwork(context)){
            String result ="{\"result\":false,\"msg\":\"网络连接不可用请检查!\"}";
            deliverDataSuccess(result, callBack);
            return;
        }
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        File file = new File(filePath);
        if(params != null){
            for(Map.Entry entry : params.entrySet()){
                requestBody.addFormDataPart(String.valueOf(entry.getKey()),String.valueOf(entry.getValue()));
            }
        }
        if(file != null){
            RequestBody body = RequestBody.create(MediaType.parse("*"),file);
            String fileName = file.getName();
            requestBody.addFormDataPart(key,file.getName(),body);
        }
        final Request request = new Request.Builder().post(requestBody.build()).url(url).build();

        Call call = client.newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    
                    deliverDataSuccess(result, callBack);
                } catch (IOException e) {
                    e.printStackTrace();
                    deliverDataFailure(request, e, callBack);
                }
            }
        });
    }



    //******************  数据分发的方法  ******************/
    private void deliverDataFailure(final Request request, final IOException e, final
    DataCallBack callBack) {
        mHandler.post(new Runnable() {//发送到主线程
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.requestFailure(request, e);
                }
            }
        });
    }

    private void deliverDataSuccess(final String result, final DataCallBack callBack) {
        mHandler.post(new Runnable() {//同样 发送到主线程
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.requestSuccess(result);
                }
            }
        });
    }


    //******************  对外调用的方法  ******************/
    public static Response getSync(String url) throws IOException {
        return getInstance().p_getSync(url);//同步GET，返回Response类型数据
    }

    public static String getSyncAsString(String url) throws IOException {
        return getInstance().p_getSyncAsString(url);//同步GET，返回String类型数据（和上面getSync方法只是返回的数据类型不同而已）
    }

    public static void getAsync(Context context,String url, DataCallBack callBack) {
        getInstance().p_getAsync(context,url, callBack);//异步GET 调用方法
    }

    public static void getHeaderAsync(Context context,String header,String url, DataCallBack callBack) {
        getInstance().p_getHeaderAsync(context,header,url, callBack);//异步GET 调用方法
    }

    public static void postAsync(Context context,String url, Map<String, String> params, DataCallBack callBack) {
        getInstance().p_postAsync(context,url, params, callBack);//POST提交表单 调用方法
    }
    public static void postHeaderAsync(Context context,String token,String url, Map<String, String> params, DataCallBack callBack) {
        getInstance().p_postHeaderAsync(context,token,url, params, callBack);//POST提交表单 调用方法
    }
    public static void postFileAsync(Context context,String url,String filePath,String key,Map<String, String> params,final DataCallBack callBack){
        getInstance().p_postFileAsync(context,url,filePath,key,params,callBack);//文件上传
    }
    public static void postHeaderFileAsync(Context context,String token,String url,String filePath,String key,Map<String, String> params,final DataCallBack callBack){
        getInstance().p_postHeaderFileAsync(context,token,url,filePath,key,params,callBack);//文件上传
    }

    //******************  数据回调接口  ******************/
    public interface DataCallBack {
        void requestFailure(Request request, IOException e);

        void requestSuccess(String result);
    }
}
