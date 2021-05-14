package com.example.dominion2;

import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.*;
import android.os.Bundle;

public class CardMenu extends AppCompatActivity {
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_menu);
        db = openOrCreateDatabase("Dominion.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);

    }
}
