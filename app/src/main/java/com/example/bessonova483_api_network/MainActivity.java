package com.example.bessonova483_api_network;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

EditText log, pas;
String APIAddress;
    Toast msgerr;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log = findViewById(R.id.EditTxtLog);
        pas = findViewById(R.id.EditTxtPas);
        Intent iAddress = getIntent();
        APIAddress = iAddress.getStringExtra("API address");
        msgerr=Toast.makeText(this, "", Toast.LENGTH_SHORT);
    }

    public void onEnterClick(View v)throws JSONException {
        Intent iLogIn = new Intent(this, MainMenuActivity.class);
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

                    iLogIn.putExtra("tok", token);
                    startActivity(iLogIn);


                }
            }
        };
        JSONObject obj = new JSONObject();
        obj.put("usr", log.getText().toString());
        obj.put("pass", pas.getText().toString());


        String payload=obj.toString();
        req.Post("http://v1.fxnode.ru:8081/rpc/open_session", payload);

    }

    public void onRegisterClick(View v){
        Intent iRegister = new Intent(this, RegisterActivity.class);
        startActivity(iRegister);

    }

    public void onChangeAPIAddressClick(View v){
        Intent iChange = new Intent(this, ChangeAPIAddressActivity.class);
        startActivity(iChange);
    }


}