package com.example.miprimeraapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDSQLite extends SQLiteOpenHelper {

    public static final String tableUser = "CREATE TABLE usuario (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "cedula TEXT, " +
            "nombresapellidos TEXT," +
            "edad INTEGER, " +
            "nacionalidad TEXT, " +
            "genero TEXT, " +
            "estadoCivil TEXT, " +
            "fechaNacimiento TEXT, " +
            "ratingUser FLOAT)";

    public static final String dbName = "dbGrupo6.sqlite";
    public static final int dbVersion = 8;

    public BDSQLite(Context context){
        super(context, dbName, null , dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sq) {
        sq.execSQL(tableUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS usuario");
        onCreate(sqLiteDatabase);
    }
}
