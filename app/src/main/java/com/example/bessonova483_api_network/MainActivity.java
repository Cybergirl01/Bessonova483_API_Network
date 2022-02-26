package com.example.bessonova483_api_network;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

EditText log, pas;
String APIAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log = findViewById(R.id.EditTxtLog);
        pas = findViewById(R.id.EditTxtPas);
        Intent iAddress = getIntent();
        APIAddress = iAddress.getStringExtra("API address");
    }

    public void onEnterClick(View v){

Intent iEnter = new Intent(this, MainMenuActivity.class);
startActivity(iEnter);
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