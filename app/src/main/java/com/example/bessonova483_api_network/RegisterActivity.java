package com.example.bessonova483_api_network;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    EditText log, pas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        log = findViewById(R.id.EditTxtLog);
        pas = findViewById(R.id.EditTxtPas);
    }

    public void onOKclick (View v){


    }

    public void onCancelClick(View v){
        Intent iBack = new Intent(this, MainActivity.class);
        startActivity(iBack);
    }
}