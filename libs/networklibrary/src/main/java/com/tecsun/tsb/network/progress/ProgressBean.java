package com.tecsun.tsb.network.progress;

/**
 * Created by yeqw on 2018/7/16.
 */

public class ProgressBean {
    private long progress;
    private long total;
    private boolean done;

    public long getBytesRead() {
        return progress;
    }

    public void setBytesRead(long progress) {
        this.progress = progress;
    }

    public long getContentLength() {
        return total;
    }

    public void setContentLength(long total) {
        this.total = total;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done){
        this.done = done;
    }

}
