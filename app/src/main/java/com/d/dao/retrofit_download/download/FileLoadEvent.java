package com.d.dao.retrofit_download.download;

/**
 * Created by dao on 7/10/16.
 */
public class FileLoadEvent {
    long total;
    long progress;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public FileLoadEvent(long total, long progress) {
        this.total = total;
        this.progress = progress;
    }
}
