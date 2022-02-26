package com.example.bessonova483_api_network;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ChangeAPIAddressActivity extends AppCompatActivity {

EditText api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_apiaddress);
        api = findViewById(R.id.EditAPI);
    }

    public void onOKClick(View v){

        String address = api.getText().toString();
        Intent iMain = new Intent(this, MainActivity.class);
        iMain.putExtra("API address", address);
        startActivity(iMain);
    }

    public void onCancelClick(View v){

        Intent iBack = new Intent(this, MainActivity.class);
        startActivity(iBack);
    }
}