
package com.bhavadeep.kanopy_project.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterCommit {

    @SerializedName("sha")
    @Expose
    private String sha;
    @SerializedName("commit")
    @Expose
    private Commit commit;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("html_url")
    @Expose
    private String htmlUrl;
    @SerializedName("comments_url")
    @Expose
    private String commentsUrl;
    @SerializedName("author")
    @Expose
    private AuthorGlobal author;
    @SerializedName("committer")
    @Expose
    private CommitterGlobal committer;
    @SerializedName("parents")
    @Expose
    private List<Parent> parents = null;

    public String getSha() {
        return sha;
    }

    public Commit getCommit() {
        return commit;
    }

    public String getUrl() {
        return url;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }

    public AuthorGlobal getAuthor() {
        return author;
    }

    public void setAuthor(AuthorGlobal author) {
        this.author = author;
    }

    public CommitterGlobal getCommitter() {
        return committer;
    }

    public List<Parent> getParents() {
        return parents;
    }

}
