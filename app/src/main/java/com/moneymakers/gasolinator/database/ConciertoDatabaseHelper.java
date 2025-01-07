package com.moneymakers.gasolinator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ConciertoDatabaseHelper extends SQLiteOpenHelper {

    // Database Information
    private static final String DATABASE_NAME = "conciertos.db";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    private static final String TABLE_CONCIERTOS = "conciertos";

    // Table Columns
    private static final String COLUMN_ID_CONCIERTO = "idConcierto";
    private static final String COLUMN_NOMBRE_CONCIERTO = "nombreConcierto";
    private static final String COLUMN_FECHA_CONCIERTO = "fechaConcierto";
    private static final String COLUMN_RESUMEN_VIAJE = "resumenViaje";

    // SQL to create the table
    private static final String SQL_CREATE_TABLE_CONCIERTOS =
            "CREATE TABLE " + TABLE_CONCIERTOS + " (" +
                    COLUMN_ID_CONCIERTO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NOMBRE_CONCIERTO + " TEXT," +
                    COLUMN_FECHA_CONCIERTO + " TEXT," +
                    COLUMN_RESUMEN_VIAJE + " TEXT)";

    // SQL to delete the table
    private static final String SQL_DELETE_TABLE_CONCIERTOS =
            "DROP TABLE IF EXISTS " + TABLE_CONCIERTOS;

    public ConciertoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_CONCIERTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_CONCIERTOS);
        onCreate(db);
    }

    // Method to add a new concert
    public long addConcierto(ConciertoSQL concierto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE_CONCIERTO, concierto.getNombreConcierto());
        values.put(COLUMN_FECHA_CONCIERTO, concierto.getFechaConcierto());
        values.put(COLUMN_RESUMEN_VIAJE, concierto.getResumenViaje());

        long newRowId = db.insert(TABLE_CONCIERTOS, null, values);
        db.close();
        return newRowId;
    }

    // Method to get all concerts
    public List<ConciertoSQL> getAllConciertos() {
        List<ConciertoSQL> conciertos = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CONCIERTOS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ConciertoSQL concierto = new ConciertoSQL(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_CONCIERTO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE_CONCIERTO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA_CONCIERTO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESUMEN_VIAJE))
                );
                conciertos.add(concierto);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return conciertos;
    }
}