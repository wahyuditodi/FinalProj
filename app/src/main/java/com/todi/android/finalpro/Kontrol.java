package com.todi.android.finalpro;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;


public class Kontrol extends Application {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private static final String TAG = Kontrol.class.getSimpleName();

    private RequestQueue mRequesQueue;

    private static Kontrol mInstance;

    @Override
    public void onCreate(){
        super.onCreate();
        mInstance = this;
        Fresco.initialize(this);
    }

    public static synchronized Kontrol getInstance(){
        return mInstance;
    }

    private RequestQueue getRequesQueue(){
        if (mRequesQueue == null){
            mRequesQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequesQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag){
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequesQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req){
        req.setTag(TAG);
        getRequesQueue().add(req);
    }

    public void cancelPendingRequests(Object tag){
        if (mRequesQueue != null){
            mRequesQueue.cancelAll(tag);
        }
    }
}
