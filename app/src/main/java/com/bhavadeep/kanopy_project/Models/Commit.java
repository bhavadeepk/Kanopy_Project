
package com.bhavadeep.kanopy_project.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Commit {

    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("committer")
    @Expose
    private Committer committer;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("tree")
    @Expose
    private Tree tree;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("comment_count")
    @Expose
    private Integer commentCount;
    @SerializedName("verification")
    @Expose
    private Verification verification;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Committer getCommitter() {
        return committer;
    }

    public void setCommitter(Committer committer) {
        this.committer = committer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Tree getTree() {
        return tree;
    }

    public String getUrl() {
        return url;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public Verification getVerification() {
        return verification;
    }

    // Parse the raw message into clean multiline message
    public String parseMessage(){
        String messages[] = message.split("\\n");
        StringBuilder stringBuilder = new StringBuilder();
        for(String lineMessage : messages){
            lineMessage += "\n";
            stringBuilder.append(lineMessage);
        }
        return stringBuilder.toString();
    }

}
