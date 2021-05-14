package com.game.dominion2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.sql.SQLException;
import java.util.Scanner;

import com.game.dominion2.DataBaseHelper;

public class DBManager {
    private Context context;
    private SQLiteDatabase database;
    private DataBaseHelper dbHelper;

    public DBManager(Context c) {
        this.context = c;
    }
    public DBManager Open()throws SQLException{
        this.dbHelper = new DataBaseHelper(this.context);
        this.database = this.dbHelper.getWritableDatabase();
        GetContentValues();
        return this;
    }
    public void Close() {
        this.dbHelper.close();
    }
    public Cursor Fetch(){
        Cursor cursor = this.database.query(DataBaseHelper.DOMINION_CARDS_TABLE, new String[]{DataBaseHelper.COL_CARD_NAME, DataBaseHelper.COL_TYPE, DataBaseHelper.COL_COST, DataBaseHelper.COL_ACTION, DataBaseHelper.COL_BUY, DataBaseHelper.COL_DRAW, DataBaseHelper.COL_COINS, DataBaseHelper.COL_PAYLOAD, DataBaseHelper.COL_TRASH, DataBaseHelper.COL_VP}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    private void GetContentValues(){

        try {
            File file = new File("dominion_cards.txt");
            if (!file.exists()) {
                System.out.print("Error: Card file does not exist...");
                return;
            }
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] CardInfo = line.split("|");
                String name = "";
                int action = 0;
                int draw = 0;
                int victory_points = 0;
                int cost = 0;
                int buy = 0;
                int coins = 0;
                String type = "";
                boolean trash = false;
                boolean payload = false;
                for (String str : CardInfo) {
                    String[] info = str.split(":");
                    switch (info[0]){
                        case "name":
                            name = info[1];
                            break;
                        case "cost":
                            cost = Integer.parseInt(info[1]);
                        case "type":
                            type = info[1];
                        case "A":
                            action += Integer.parseInt(info[1]);
                        case "B":
                            buy += Integer.parseInt(info[1]);
                        case "C":
                            draw += Integer.parseInt(info[1]);
                        case "T":
                            if(info[1].equals("True")){
                                trash = true;
                            }
                        case "VP":
                            victory_points = Integer.parseInt(info[1]);
                        case "V":
                            coins = Integer.parseInt(info[1]);
                        default:
                            break;
                    }
                }
                // have card info in variables -- construct string for sql
                ContentValues values = new ContentValues();

                values.put(dbHelper.COL_TYPE, type);
                values.put(dbHelper.COL_VP, victory_points);
                values.put(dbHelper.COL_TRASH, trash);
                values.put(dbHelper.COL_PAYLOAD, payload);
                values.put(dbHelper.COL_DRAW, draw);
                values.put(dbHelper.COL_COINS, coins);
                values.put(dbHelper.COL_ACTION, action);
                values.put(dbHelper.COL_BUY, buy);
                values.put(dbHelper.COL_CARD_NAME, name);
                values.put(dbHelper.COL_COST, cost);
                this.database.insert(dbHelper.DOMINION_CARDS_TABLE, null, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }
}
