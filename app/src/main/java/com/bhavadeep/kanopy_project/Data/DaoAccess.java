package com.bhavadeep.kanopy_project.Data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bhavadeep.kanopy_project.Models.MasterCommit;

import java.util.List;


@Dao
public interface DaoAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMutipleCommits(List<CommitEntity> commits);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCommit(CommitEntity commit);

    @Query("SELECT * FROM COMMIT_ENTITY")
    List<CommitEntity> fetchAllCommits();

    @Update
    void updateRecord(CommitEntity commit);

    @Delete
    void deleteRecord(CommitEntity commit);

    @Delete
    void deleteAll(CommitEntity... commitEntities);

}

