package com.game.dominion2;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.*;
import android.os.Bundle;

import com.game.dominion2.CardMenu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void LoadCardMenu(View view){
        Intent intent = new Intent(MainActivity.this, CardMenu.class);
        startActivity(intent);
    }
}