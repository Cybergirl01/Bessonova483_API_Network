package com.example.bessonova483_api_network;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainMenuActivity extends AppCompatActivity {
String token;
    Toast msgerr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Intent itoken = getIntent();
        token = itoken.getStringExtra("tok");
    }

    public void onLogOutClick(View v)throws JSONException {
        Intent ilogout = new Intent(this, MainActivity.class);
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


                    startActivity(ilogout);

                }
            }
        };
        JSONObject obj = new JSONObject();
        obj.put("skey",token);
        String payload=obj.toString();
        req.Post("http://v1.fxnode.ru:8081/rpc/close_session", payload);

    }

    public void onViewListClick(View v){
        Intent iViewList = new Intent(this, ListMapsActivity.class);
        startActivity(iViewList);
    }

    public void onUpdateUserClick(View v){
        Intent iUpdate = new Intent(this, UpdateMap.class);
        startActivity(iUpdate);
    }
    public void onSessionsClick(View v){
        Intent iSessions = new Intent(this, SessionsListActivity.class);
        iSessions.putExtra("tok", token);
        startActivity(iSessions);
    }
}