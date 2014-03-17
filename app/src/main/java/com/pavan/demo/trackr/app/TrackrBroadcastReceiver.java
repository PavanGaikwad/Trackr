package com.pavan.demo.trackr.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by ginfotech on 9/3/14.
 */
public class TrackrBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            Bundle bundle = intent.getExtras();
            String message = bundle.getString("alarm_message");
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();


                        callWebService(context);



;

        } catch (Exception e) {
            Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }
    }

    public void callWebService(Context context) throws Exception{
        String URL = "http://pavangaikwad.aws.af.cm/trackr/update?";
        String result = "";

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        String device_id = tm.getDeviceId();

        String q="userid="+device_id+"&"+getLocation(context);
        Toast.makeText(context,"Url : "+URL+q,Toast.LENGTH_LONG);
        final HttpClient httpclient = new DefaultHttpClient();
        final HttpGet request = new HttpGet(URL + q);
        //request.addHeader("deviceId", device_id);


                    Toast.makeText(context,"Url : "+URL+q,Toast.LENGTH_LONG);
                    ResponseHandler<String> handler = new BasicResponseHandler();
                    httpclient.execute(request, handler);





        httpclient.getConnectionManager().shutdown();

    }

    public String getLocation(Context context){
        GPSTracker gpsTracker = new GPSTracker(context);
        String toreturn=null;
        if (gpsTracker.canGetLocation())
        {
            String stringLatitude = String.valueOf(gpsTracker.latitude);


            String stringLongitude = String.valueOf(gpsTracker.longitude);


            String country = gpsTracker.getCountryName(context);

            String city = gpsTracker.getLocality(context);


            String postalCode = gpsTracker.getPostalCode(context);


            String addressLine = gpsTracker.getAddressLine(context);
            toreturn = "latitude="+stringLatitude+"&longitude="+stringLongitude;
        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            Toast.makeText(context, "Enable GPS", Toast.LENGTH_LONG);
        }
    return toreturn;
    }

    }

