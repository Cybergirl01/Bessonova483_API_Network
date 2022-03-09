package com.example.bessonova483_api_network;

import android.app.Activity;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIClass {
    Activity activity;
    public void onSuccess(String data){

    }
    public void onError(){

    }

    public APIClass(Activity activity){
        this.activity = activity;
    }
    public void Post(String endpoint, String payload){
        Thread t = new Thread(()-> {
            try{
                Log.e("123", payload);
                URL url = new URL(endpoint);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);
                Log.e("123", payload);

                OutputStream out_stream = con.getOutputStream();
                Log.e("123", payload);
                byte[] out = payload.getBytes("utf-8");
                out_stream.write(out);

                InputStream in_stream = con.getInputStream();
                byte[] in = new byte[1024];
                String data = "";
                while (true){
                    int len = in_stream.read(out);
                    if (len <0)break;
                    data+=new String(out, 0, len);

                }
                con.disconnect();
                final String res = data;
                activity.runOnUiThread(() -> {onSuccess(res);});
            }catch (Exception e){
                e.printStackTrace();
                activity.runOnUiThread(() ->{onError();});
            }

        });
        t.start();
    }
}

