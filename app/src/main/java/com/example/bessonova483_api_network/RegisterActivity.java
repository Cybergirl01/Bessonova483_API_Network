package com.example.bessonova483_api_network;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    EditText log, pas;
    Toast msgerr;
    String Usr, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        log = findViewById(R.id.EditTxtLog);
        pas = findViewById(R.id.EditTxtPas);
        msgerr=Toast.makeText(this, "", Toast.LENGTH_SHORT);
    }

    public void onOKclick (View v)throws JSONException {
        Intent i = new Intent(this, MainActivity.class);
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
                    Usr = data.replace("\"", "");
                    pass = data.replace("\"", "");
                    startActivity(i);


                }
            }
        };
        JSONObject obj = new JSONObject();
        obj.put("usr", log.getText().toString());
        obj.put("pass", pas.getText().toString());


        String payload=obj.toString();
        req.Post("http://v1.fxnode.ru:8081/rpc/add_account", payload);
    }

    public void onCancelClick(View v){
        Intent iBack = new Intent(this, MainActivity.class);
        startActivity(iBack);
    }
}