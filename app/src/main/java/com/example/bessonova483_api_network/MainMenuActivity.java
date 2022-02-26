package com.example.bessonova483_api_network;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void onLogOutClick(View v){
        Intent iLogOut = new Intent(this, MainActivity.class);
        startActivity(iLogOut);
    }

    public void onViewListClick(View v){
        Intent iViewList = new Intent(this, ListMapsActivity.class);
        startActivity(iViewList);
    }

    public void onUpdateUserClick(View v){
        Intent iUpdate = new Intent(this, UpdateUserActivity.class);
        startActivity(iUpdate);
    }
}