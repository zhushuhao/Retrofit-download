package com.d.dao.retrofit_download.download;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

/**
 * Created by dao on 7/10/16.
 */
public class FileResponseBody extends ResponseBody {
    Response originResponse;

    public FileResponseBody(Response originResponse) {
        this.originResponse = originResponse;
    }

    @Override
    public MediaType contentType() {
        return originResponse.body().contentType();
    }

    @Override
    public long contentLength() {
        return originResponse.body().contentLength();
    }

    @Override
    public BufferedSource source() {
        return Okio.buffer(new ForwardingSource(originResponse.body().source()) {

            long byteReaded = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                byteReaded += bytesRead;
                RxBus.getDefault().post(new FileLoadEvent(contentLength(), byteReaded));
                return bytesRead;
            }
        });
    }
}
