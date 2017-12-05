package com.bhavadeep.kanopy_project.Data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.bhavadeep.kanopy_project.Models.MasterCommit;


@Database(entities = {CommitEntity.class}, version = 3)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract DaoAccess getDaoAccess();

    private static LocalDatabase INSTANCE;

    public static synchronized LocalDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, LocalDatabase.class, "commit_entity").fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }

    public void destroyInstance()
    {
        INSTANCE = null;
    }
}
