package com.game.dominion2;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CardCursorAdapter extends CursorAdapter{

    public CardCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.activity_card_cursor, parent, false);
    }

    // https://guides.codepath.com/android/Populating-a-ListView-with-a-CursorAdapter
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView uxTextView = (TextView) view.findViewById(R.id.uxTextView);
        String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
        uxTextView.setText(body);
    }
}
