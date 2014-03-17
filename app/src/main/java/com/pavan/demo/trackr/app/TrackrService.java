package com.pavan.demo.trackr.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by ginfotech on 9/3/14.
 */
public class TrackrService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
