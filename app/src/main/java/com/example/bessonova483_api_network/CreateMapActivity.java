package com.example.bessonova483_api_network;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateMapActivity extends AppCompatActivity {
EditText mapname;
    Toast msgerr;
    String name;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapcreate);
        mapname = findViewById(R.id.EDItTxtmapname);
        Intent iAddmap = getIntent();
        token = iAddmap.getStringExtra("token");
    }

    public void onAddMapClick(View v)
    {
        Intent ilistmap = new Intent(this, ListMapsActivity.class);
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
                    name = data.replace("\"", "");
                    startActivity(ilistmap);


                }
            }
        };
        JSONObject obj = new JSONObject();

        try {
            obj.put("mname", mapname.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            obj.put("tok", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String payload=obj.toString();
        req.Post("http://v1.fxnode.ru:8081/rpc/add_map", payload);
    }

    public void onCancelClick(View v){
        Intent iBack = new Intent(this, ListMapsActivity.class);
        startActivity(iBack);
    }
}