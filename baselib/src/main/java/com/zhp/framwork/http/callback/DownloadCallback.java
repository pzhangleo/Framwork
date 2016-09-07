package com.zhp.framwork.http.callback;

import java.io.File;

/**
 * 下载回调
 * Created by zhangpeng on 16/2/18.
 */
public abstract class DownloadCallback extends ResponseCallbackImpl<File> {
    @Override
    public abstract void onSuccess(File baseData);

    @Override
    public abstract void onFail(int statusCode, File failDate, Throwable error);
}
