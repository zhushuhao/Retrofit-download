package com.d.dao.retrofit_download.download;

import java.io.IOException;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by dao on 7/10/16.
 */
public class FileApi {
    private static final int DEFAULT_TIMEOUT = 8;
    private Retrofit retrofit;
    private FileService fileService;
    private volatile static FileApi instance;
    private static Call<ResponseBody> call;

   private static Hashtable<String,FileApi> mFileHashTable;

    static{
        mFileHashTable = new Hashtable<>();
    }

    private FileApi(String baseUrl){
        retrofit = new Retrofit.Builder()
                .client(initOkHttpClient())
                .baseUrl(baseUrl)
                .build();
        fileService = retrofit.create(FileService.class);
    }

    public static FileApi getInstance(String baseUrl){
        instance = mFileHashTable.get(baseUrl);
        if(instance==null){
            synchronized (FileApi.class){
                if(instance==null){
                    instance = new FileApi(baseUrl);
                    mFileHashTable.put(baseUrl,instance);
                }
            }
        }
        return instance;
    }

    public void loadFileByName(String fileName,FileCallback callback){
        call = fileService.loadFile(fileName);
        call.enqueue(callback);
    }

    public static void cancelLoading(){
        if(call!=null && call.isCanceled()==false){
            call.cancel();
        }
    }

    private OkHttpClient initOkHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originResponse = chain.proceed(chain.request());

                return originResponse.newBuilder()
                        .body(new FileResponseBody(originResponse))
                        .build();
            }
        });
        return builder.build();
    }






}
