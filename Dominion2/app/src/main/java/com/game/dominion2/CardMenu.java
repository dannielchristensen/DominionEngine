package com.game.dominion2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.*;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.widget.CursorAdapter;
import android.widget.ListView;


public class CardMenu extends AppCompatActivity {
    SQLiteDatabase db;
    CursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_menu);
        DBManager dbManager = new DBManager(getApplicationContext());
        ListView items = (ListView) findViewById(R.id.uxListView);

        try {
            dbManager.Open();
            Cursor cursor = dbManager.Fetch();
            adapter = new CardCursorAdapter(this, cursor);
            items.setAdapter(adapter);
            cursor.close();
            dbManager.Close();
        }catch(Exception e){
            e.printStackTrace();
            String s = null;

        }

        System.out.print("database created...");


    }
}
