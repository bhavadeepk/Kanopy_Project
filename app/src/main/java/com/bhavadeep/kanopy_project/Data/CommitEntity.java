package com.bhavadeep.kanopy_project.Data;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "commit_entity")
public class CommitEntity implements Comparable<CommitEntity>{
    @Ignore
    private String sha;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "image")
    private String imageUrl;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "url")
    private String htmlUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    @ColumnInfo(name = "profile")
    private String profileUrl;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "message")
    private String message;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int compareTo(@NonNull CommitEntity other) {
        return author.compareTo(other.getAuthor());
    }

    public Date changeToDateFormat(){
        SimpleDateFormat df = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.US);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
