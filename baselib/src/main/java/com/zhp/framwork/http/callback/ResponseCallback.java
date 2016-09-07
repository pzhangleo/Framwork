package com.zhp.framwork.http.callback;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 基础网络请求回调
 * GSON_TYPE为BaseObject的子类泛型
 * Created by Zhp on 2014/5/16.
 */
public abstract class ResponseCallback<GSON_TYPE> {

    protected Request mRequest;

    private boolean mUseOfflineCache;

    /**
     * 同步模式,在调用者的线程中执行
     */
    private boolean mSync;

    public ResponseCallback() {
    }

    /**
     * useOfflineCache为true时，会在网络断开时获取cache数据返回
     * 这种情况下会覆盖server的cache control字段，然后通过okhttp force cache完成强制使用cache
     * @param useOfflineCache
     */
    public ResponseCallback(boolean useOfflineCache) {
        mUseOfflineCache = useOfflineCache;
    }

    public ResponseCallback(boolean useOfflineCache, boolean sync) {
        mUseOfflineCache = useOfflineCache;
        mSync = sync;
    }

    /**
     * 请求开始
     */
    public abstract void onStart();

    /**
     * 请求进度
     * @param bytesWritten 已写入
     * @param totalSize    总数
     * @param download
     */
    public abstract void onProgress(long bytesWritten, long totalSize, boolean download);

    /**
     * 请求成功
     * @param baseData 已解析的数据
     */
    public abstract void onSuccess(GSON_TYPE baseData);

    /**
     * 解析返回数据
     *
     * @param response 返回数据
     * @return
     */
    public abstract GSON_TYPE parseResponse(Response response) throws IOException;

    /**
     * 请求失败
     * 可能是业务逻辑的失败，或者是http请求失败
     * @param statusCode 0为无需特别处理。
     * @param failDate  失败的信息
     * @param error    具体的错误
     */
    public abstract void onFail(int statusCode, GSON_TYPE failDate, Throwable error);

    public Request updateRequestHeaders(Request request) {
        return request;
    }

    /**
     * 本地缓存获取成功
     * @param responseJsonType 本地缓存数据
     * @param time             缓存被修改的时间
     *                         用来判断该缓存是否有效
     */
    public abstract void onCacheLoaded(GSON_TYPE responseJsonType, long time);

    /**
     * 请求完成，无论失败或者成功，都会调用该方法。
     */
    public abstract void onFinish();

    /**
     * 需要提供Gson对象
     * 用于返回数据解析
     * @return Gson对象
     */
    public abstract Gson getGson();

    /**
     * 返回需要解析的数据对象Class
     * @return 需要解析的数据对象Class
     */
    public abstract Type getClazz();

    public Request getRequest() {
        return mRequest;
    }

    public void setRequest(Request request) {
        this.mRequest = request;
    }

    public boolean isUseOfflineCache() {
        return mUseOfflineCache;
    }

    public void setUseOfflineCache(boolean useOfflineCache) {
        mUseOfflineCache = useOfflineCache;
    }

    public boolean isSync() {
        return mSync;
    }

    public void setSync(boolean sync) {
        mSync = sync;
    }
}
