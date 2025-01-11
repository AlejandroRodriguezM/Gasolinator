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

    public ConciertoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_CONCIERTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONCIERTOS);
        onCreate(db);
    }

    // Insert a new concierto
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

    // Get the database path
    public String getDatabasePath() {
        return this.getReadableDatabase().getPath();
    }

    // Get all conciertos
    public List<ConciertoSQL> getAllConciertos() {
        List<ConciertoSQL> conciertos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONCIERTOS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int idConcierto = cursor.getColumnIndex(COLUMN_ID_CONCIERTO);
                int nombreConciertoIndex = cursor.getColumnIndex(COLUMN_NOMBRE_CONCIERTO);
                int fechaConciertoIndex = cursor.getColumnIndex(COLUMN_FECHA_CONCIERTO);
                int resumenViajeIndex = cursor.getColumnIndex(COLUMN_RESUMEN_VIAJE);

                int id = idConcierto != -1 ? cursor.getInt(idConcierto) : 0;
                String nombreConcierto = nombreConciertoIndex != -1 ? cursor.getString(nombreConciertoIndex) : "";
                String fechaConcierto = fechaConciertoIndex != -1 ? cursor.getString(fechaConciertoIndex) : "";
                String resumenViaje = resumenViajeIndex != -1 ? cursor.getString(resumenViajeIndex) : "";

                ConciertoSQL concierto = new ConciertoSQL(id, nombreConcierto, fechaConcierto, resumenViaje);
                conciertos.add(concierto);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return conciertos;
    }

    // Delete all conciertos
    public void deleteAllConciertos() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONCIERTOS, null, null);
        db.close();
    }

    // Delete a concierto by ID
    public void deleteConciertoById(int idConcierto) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONCIERTOS, COLUMN_ID_CONCIERTO + " = ?", new String[]{String.valueOf(idConcierto)});
        db.close();
    }
}