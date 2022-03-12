package com.example.bessonova483_api_network;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SessionsListActivity extends AppCompatActivity {
    String token;
    ListView lv;
    Toast msgerr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions_list);
        ArrayList<String> listSessions = new ArrayList<String>();
        token = getIntent().getStringExtra("token");
        msgerr=Toast.makeText(this, "", Toast.LENGTH_SHORT);
        listSessions.clear();
        JSONObject obj = new JSONObject();
        APIClass req = new APIClass(this) {

            @Override
            public void onError() {
                msgerr.setText("Request failed!");
                msgerr.show();
            }

            @Override
            public void onSuccess(String data) {

                if (data == "null") {
                    msgerr.setText("Invalid credentials!");
                    msgerr.show();
                } else {
                    token = data.replace("\"", "");
                    Log.i("API", "token=" + token);
                }
            }
        };


        try {
            obj.put("skey",token);

            JSONArray array = new JSONArray(obj);
            if(array !=null)
            {
                int len = array.length();
                for(int i=0;i<len;i++){
                    listSessions.add(array.get(i).toString());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listSessions);
                    lv.setAdapter(adapter);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String payload=obj.toString();
        req.Post("http://v1.fxnode.ru:8081/rpc/get_sessions", payload);
    }
}