package vv.game.vaibhav.tiles;

/**
 * Created by Vaibhav on 12/2/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DOZO.db";
    public static final String SCORE_TABLE_NAME = "scores";
    public static final String COLUMN_ID = "ID";
    public static final String SCORE_LEVEL_ID = "level";
    public static final String SCORE_LEVEL_SCORE = "score";
    public static final String FLAG_TABLE_NAME = "flagTable";
    public static final String FLAG_FIRST_OPEN = "flagFirstOpen";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + SCORE_TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + SCORE_LEVEL_ID + " INT, " + SCORE_LEVEL_SCORE + " INT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SCORE_TABLE_NAME);
    }

    void addScore(int level, int levelScore) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SCORE_LEVEL_ID, level);
        values.put(SCORE_LEVEL_SCORE, levelScore);
        db.insert(SCORE_TABLE_NAME, null, values);
        db.close();
    }

    int getScore(int level) {
        SQLiteDatabase db = this.getReadableDatabase();

        int score1 = -1;

        if (getCount() > 0){
            Cursor cursor = db.rawQuery("SELECT * from scores WHERE 1", null);
            cursor.moveToFirst();
            do{
                if(cursor.getInt(1) == level)
                    score1 = cursor.getInt(2);
            } while (cursor.moveToNext());
        }
        return score1;
    }

    void updateScore(int level, int levelScore) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SCORE_LEVEL_ID, level);
        values.put(SCORE_LEVEL_SCORE, levelScore);
        db.execSQL("UPDATE scores SET score = "+levelScore+" WHERE level = "+ level + ";");
    }

    int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + SCORE_TABLE_NAME, null);
        return cursor.getCount();
    }

    void deleteScore() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SCORE_TABLE_NAME, null, null);
    }
}