package com.example.dominion2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;
enum Expansions{
    BASE,
    EMPIRES,
    NOCTURNE,
    INTRIGUE
}
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DOMINION_EXPANSIONS_TABLE = "DOMINION_EXPANSIONS";
    public static final String DOMINION_CARDS_TABLE = "DOMINION_CARDS";
    public static final String tables[] = {DOMINION_CARDS_TABLE, DOMINION_EXPANSIONS_TABLE};


    public DataBaseHelper(@Nullable Context c){
        super(c, "Dominion", null, 1);
    }

    // This is called the first time a database is accessed
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createGameTable = "CREATE TABLE " + DOMINION_EXPANSIONS_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, EXPANSION_NAME TEXT)";
        String createCardTable = "CREATE TABLE " + DOMINION_CARDS_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, CARD_NAME TEXT, VICTORY_POINTS INTEGER, COIN_VALUE INTEGER, ACTION_VALUE INTEGER, CARD_VALUE INTEGER, PAYLOAD_VALUE BOOL)";
        db.execSQL(createGameTable);
        db.execSQL(createCardTable);

    }

    // This is called when database version changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (String name:tables) {
            db.execSQL("DROP TABLE IF EXISTS " + tables + "; GO");
        }
        this.onCreate(db);
    }
    private void PopulateBaseCards(SQLiteDatabase db){
        String sql = "";
        db.execSQL(sql);
    }
    private String EnumToString(Expansions e){
        switch(e){
            case BASE:
                return "base";
            case EMPIRES:
                return "empires";
            case NOCTURNE:
                return "nocturne";
            case INTRIGUE:
                return "intrigue";
            default:
                return "";
        }
    }
}
