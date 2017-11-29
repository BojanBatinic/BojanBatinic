package com.example.androiddevelopment.turistickivodic.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.androiddevelopment.turistickivodic.model.Atrakcija;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by androiddevelopment on 29.11.17..
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME    = "ormlite.db";
    private static final int    DATABASE_VERSION = 1;

    private Dao<Atrakcija,Integer> atrakcijaDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource,Atrakcija.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource,Atrakcija.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Dao<Atrakcija, Integer> getAtrakcijaDao() throws SQLException {

        if (atrakcijaDao == null) {
            atrakcijaDao = getDao(Atrakcija.class);
        }
        return atrakcijaDao;
    }

    @Override
    public void close() {
        super.close();
        atrakcijaDao = null;
    }
}
