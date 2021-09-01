package com.example.renitto.ktu_master;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Renitto on 8/4/2016.
 */
public class Application extends android.app.Application {
    public static final String TAG = Application.class
            .getSimpleName();
    private static Application mInstance;
    private RequestQueue mRequestQueue;

    public String FbAccessToken;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;



    }
    public static synchronized Application getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
//        req.setShouldCache(false); // not adding to cache
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
