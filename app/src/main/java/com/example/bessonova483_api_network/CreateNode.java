package com.example.bessonova483_api_network;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateNode extends AppCompatActivity {
    String token, agent, name, IP;
    Integer x, y;
    EditText namenode, ipaddress, xnode, ynode, agentname;
    Toast msgerr;
    Integer mid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_node);
        namenode = findViewById(R.id.EDItTxtnodename);
        Intent iAddnode = getIntent();
        token = iAddnode.getStringExtra("token");
        mid = Integer.parseInt(iAddnode.getStringExtra("mid"));
        ipaddress = findViewById(R.id.EDItTxtAgent);
        xnode = findViewById(R.id.EDItTxtX);
        ynode = findViewById(R.id.EditTxtY);
        agentname = findViewById(R.id.EDItTxtAgent);
    }

    public void onAddnodeClick(View v)
    {
        Intent ilistnode = new Intent(this, ViewNodesInMapActivity.class);
        agent = agentname.getText().toString();
        name =namenode.getText().toString();
        x = Integer.parseInt(xnode.getText().toString());
        y = Integer.parseInt(ynode.getText().toString());
        IP = ipaddress.getText().toString();
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
                    mid = Integer.parseInt(data.replace("\"", ""));
                    x = Integer.parseInt(data.replace("\"", ""));
                    y = Integer.parseInt(data.replace("\"", ""));
                    agent = data.replace("\"", "");
                    name = data.replace("\"", "");
                    IP = data.replace("\"", "");
                    startActivity(ilistnode);


                }
            }
        };
        JSONObject obj = new JSONObject();

        try {
            obj.put("mid", mid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            obj.put("tok", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            obj.put("posx", Integer.parseInt(xnode.getText().toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            obj.put("posy", Integer.parseInt(ynode.getText().toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            obj.put("aname", agentname.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            obj.put("nip", ipaddress.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            obj.put("nname", namenode.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String payload=obj.toString();
        req.Post("http://v1.fxnode.ru:8081/rpc/add_node", payload);
    }

    public void onCancelClick(View v){
        Intent iBack = new Intent(this, ListMapsActivity.class);
        startActivity(iBack);
    }
}