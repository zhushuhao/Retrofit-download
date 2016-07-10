package com.d.dao.retrofit_download.download;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by dao on 7/10/16.
 */
public interface FileService {
    /**
     * 下载数据库、资源
     *
     * @param fileName
     * @return
     */
    @GET("{fileName}")
    Call<ResponseBody> loadFile(@Path("fileName") String fileName);
}
