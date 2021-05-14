package com.game.dominion2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.content.ContentValues;

import java.io.File;
import java.util.*;
import java.lang.*;


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
    public static final String COL_CARD_NAME = "CARD_NAME";
    public static final String COL_VP = "VICTORY_POINTS";
    public static final String COL_COST = "COST_VALUE";
    public static final String COL_ACTION = "ACTION_VALUE";
    public static final String COL_DRAW = "DRAW_VALUE";
    public static final String COL_PAYLOAD = "HAS_PAYLOAD";
    public static final String COL_BUY = "BUY_VALUE";
    public static final String COL_COINS = "COIN_VALUE";
    public static final String COL_TYPE = "CARD_TYPE";
    public static final String COL_TRASH = "HAS_TRASH";
    private SQLiteDatabase db;



    public DataBaseHelper(@Nullable Context c){
        super(c, "Dominion", null, 1);
    }

    // This is called the first time a database is accessed
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createGameTable = "CREATE TABLE " + DOMINION_EXPANSIONS_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, EXPANSION_NAME TEXT)";
        String createCardTable = "CREATE TABLE " + DOMINION_CARDS_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_CARD_NAME+" TEXT, "+COL_VP+" INTEGER, "+COL_COST+" INTEGER, "+COL_ACTION+" INTEGER, "+COL_DRAW+" INTEGER, "+ COL_COINS+" INTEGER, " +COL_BUY +" INTEGER, " + COL_PAYLOAD+" BOOL, " + COL_TRASH + " BOOL, " + COL_TYPE+" TEXT)";
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
