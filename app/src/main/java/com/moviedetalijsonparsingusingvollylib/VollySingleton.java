package com.moviedetalijsonparsingusingvollylib;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by aalishan on 12/10/16.
 */
public class VollySingleton {
    public static VollySingleton sInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VollySingleton() {
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
    }

    public static VollySingleton getInstance() {
        if (sInstance == null) {
            sInstance = new VollySingleton();
        }
        return sInstance;

    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }
}
